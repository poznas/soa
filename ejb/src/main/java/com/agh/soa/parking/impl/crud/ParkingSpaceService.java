package com.agh.soa.parking.impl.crud;

import static java.util.Optional.ofNullable;
import static javax.ws.rs.core.Response.Status.BAD_REQUEST;

import com.agh.soa.lab8.service.IRestCrudService;
import com.agh.soa.parking.dao.ParkingSpaceRepository;
import com.agh.soa.parking.model.ParkingSpace;
import java.util.Map;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.core.Response;
import lombok.Getter;

@RequestScoped
public class ParkingSpaceService implements IRestCrudService<ParkingSpace> {

  @EJB
  @Getter
  ParkingSpaceRepository repository;

  public Response applyPatches(@NotNull Long zoneId, @NotNull Long spaceId,
                               @NotNull @NotEmpty Map patches) {

    var patch = ofNullable(patches.get("occupied"))
      .filter(Boolean.class::isInstance)
      .map(Boolean.class::cast);

    return ofNullable(repository.getSpace(zoneId, spaceId))
      .filter(space -> patch.isPresent())
      .map(space -> {
        space.setOccupied(patch.get());
        return merge(space);
      })
      .orElse(Response.status(BAD_REQUEST).build());
  }
}
