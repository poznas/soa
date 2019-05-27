package com.agh.soa.parking.impl.crud;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import com.agh.soa.lab8.service.IRestCrudService;
import com.agh.soa.parking.dao.ParkingSpaceRepository;
import com.agh.soa.parking.dao.ParkingTicketRepository;
import com.agh.soa.parking.model.ParkingSpace;
import com.agh.soa.parking.model.ParkingTicket;
import com.agh.soa.parking.model.TicketType;
import com.agh.soa.parking.timer.SpaceTicketTimer;
import java.util.Timer;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@RequestScoped
public class ParkingTicketService implements IRestCrudService<ParkingTicket> {

  @EJB
  @Getter
  ParkingTicketRepository repository;
  @EJB
  ParkingSpaceRepository spaceRepository;

  private transient Timer timer = new Timer();


  public Response postTicket(@NotNull Long zoneId, @NotNull Long spaceId,
                             @NotNull TicketType ticketType) {

    return ofNullable(spaceRepository.getSpace(zoneId, spaceId))
      .map(space -> {

        var ticket = buildTicket(ticketType, space);
        ticket = getRepository().insert(ticket);

        spawnTicketTimer(zoneId, spaceId, ticket);

        return Response.status(CREATED).build();
      })
      .orElse(Response.status(NOT_FOUND).build());
  }

  private static ParkingTicket buildTicket(@NotNull TicketType ticketType, ParkingSpace space) {
    return ParkingTicket.builder()
      .space(space).purchaseTime(now()).type(ticketType)
      .expireTime(now().plusSeconds(ticketType.getExpireSeconds())).build();
  }

  private void spawnTicketTimer(@NotNull Long zoneId, @NotNull Long spaceId, ParkingTicket ticket) {
    SpaceTicketTimer.builder().timer(timer)
      .spaceSupplier(() -> spaceRepository.getSpace(zoneId, spaceId))
      .activeTicketSupplier(() -> repository.getActiveTicket(zoneId, spaceId))
      .workerNotifier(() -> log.info(format("Notifying zone [%d] workers", zoneId)))
      .space(ticket.getSpace())
      .ticket(ticket)
      .build().schedule();
  }
}
