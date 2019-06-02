package com.agh.soa.parking.rest;

import static com.agh.soa.parking.rest.ParkingController.CONTEXT;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import com.agh.soa.parking.impl.crud.ParkingSpaceService;
import com.agh.soa.parking.impl.crud.ParkingTicketService;
import com.agh.soa.parking.impl.crud.ParkingZoneService;
import com.agh.soa.parking.model.TicketType;
import io.swagger.annotations.Api;
import java.util.Map;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Api("Parking")
@RequestScoped
@Path(CONTEXT)
@Produces(APPLICATION_JSON)
public class ParkingController {

  static final String CONTEXT = "parking";

  @Inject
  ParkingZoneService zoneService;
  @Inject
  ParkingSpaceService spaceService;
  @Inject
  ParkingTicketService ticketService;

  @GET
  @Path("/zones")
  public Response getZones() {
    return zoneService.findAll();
  }

  @PUT //@PATCH makes whole controller ignored by Apiee(Swagger facade)
  @Path("/zones/{zoneId}/spaces/{spaceId}")
  public Response patchSpace(@NotNull @PathParam("zoneId") Long zoneId,
                             @NotNull @PathParam("spaceId") Long spaceId,
                             @NotNull @NotEmpty Map<String, ?> patches) {
    return spaceService.applyPatches(zoneId, spaceId, patches);
  }

  @POST
  @Path("/zones/{zoneId}/spaces/{spaceId}/tickets")
  public Response postTicket(@NotNull @PathParam("zoneId") Long zoneId,
                             @NotNull @PathParam("spaceId") Long spaceId,
                             @QueryParam("type") @NotNull TicketType ticketType) {
    return ticketService.postTicket(zoneId, spaceId, ticketType);
  }

  @GET
  @Path("/tickets")
  public Response getTicket() {
    return ticketService.findAll();
  }
}
