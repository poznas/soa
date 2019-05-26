package com.agh.soa.parking.impl.crud;

import static java.time.LocalDateTime.now;
import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import com.agh.soa.lab8.service.IRestCrudService;
import com.agh.soa.parking.dao.ParkingSpaceRepository;
import com.agh.soa.parking.dao.ParkingTicketRepository;
import com.agh.soa.parking.model.ParkingTicket;
import com.agh.soa.parking.model.TicketType;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import lombok.Getter;

@RequestScoped
public class ParkingTicketService implements IRestCrudService<ParkingTicket> {

  @EJB
  @Getter
  ParkingTicketRepository repository;
  @EJB
  ParkingSpaceRepository spaceRepository;

  public Response postTicket(@NotNull Long zoneId, @NotNull Long spaceId,
                             @NotNull TicketType ticketType) {

    return ofNullable(spaceRepository.getSpace(zoneId, spaceId))
      .map(space -> {
        var ticket = new ParkingTicket(null, now(), ticketType, space);
        ticket = getRepository().insert(ticket);

        //TODO: spawn timers

        return Response.status(CREATED).build();
      })
      .orElse(Response.status(NOT_FOUND).build());

  }
}
