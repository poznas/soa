package com.agh.soa.parking.impl.crud;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import com.agh.soa.lab8.service.IRestCrudService;
import com.agh.soa.parking.dao.ParkingSpaceRepository;
import com.agh.soa.parking.dao.ParkingTicketRepository;
import com.agh.soa.parking.jms.ParkingWorkerNotifier;
import com.agh.soa.parking.model.entity.ParkingSpace;
import com.agh.soa.parking.timer.SpaceOccupationTimer;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@ApplicationScoped
public class ParkingSpaceService implements IRestCrudService<ParkingSpace> {

  @EJB
  @Getter
  ParkingSpaceRepository repository;
  @EJB
  ParkingTicketRepository ticketRepository;
  @EJB
  ParkingWorkerNotifier workerNotifier;

  private transient Timer timer = new Timer();

  private transient Map<Long, Map<Long, SpaceOccupationTimer>> occupationTimers
    = new ConcurrentHashMap<>();


  public Response applyPatches(@NotNull Long zoneId, @NotNull Long spaceId,
                               @NotNull @NotEmpty Map patches) {

    var patch = ofNullable(patches.get("occupied"))
      .filter(Boolean.class::isInstance)
      .map(Boolean.class::cast);

    return ofNullable(repository.getSpace(zoneId, spaceId))
      .filter(space -> patch.isPresent())
      .map(space -> {
        space.setOccupied(patch.get());

        spawnOccupationTimer(zoneId, spaceId, space);

        return merge(space);
      })
      .orElse(Response.status(BAD_REQUEST).build());
  }

  private void spawnOccupationTimer(@NotNull Long zoneId, @NotNull Long spaceId,
                                    ParkingSpace parkingSpace) {

    var zoneOccupationTimers
      = of(zoneId).map(occupationTimers::get).orElse(new ConcurrentHashMap<>());

    of(spaceId).map(zoneOccupationTimers::get).ifPresent(TimerTask::cancel);

    of(parkingSpace)
      .filter(ParkingSpace::isOccupied)
      .map(space ->
        new SpaceOccupationTimer(timer,
          () -> repository.getSpace(zoneId, spaceId),
          () -> ticketRepository.getActiveTicket(zoneId, spaceId),
          () -> workerNotifier.unauthorizedSpaceOccupation(space),
          space)
      )
      .ifPresent(task -> {
        zoneOccupationTimers.put(spaceId, task);
        occupationTimers.put(zoneId, zoneOccupationTimers);
        task.schedule();
      });
  }
}
