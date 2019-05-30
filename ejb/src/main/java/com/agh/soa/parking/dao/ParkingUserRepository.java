package com.agh.soa.parking.dao;

import static com.agh.soa.utils.CollectionUtils.filterList;
import static java.util.Optional.ofNullable;
import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.parking.model.ParkingUser;
import com.agh.soa.parking.model.ParkingZone;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.validation.constraints.NotNull;

@Stateful
@TransactionManagement(BEAN)
public class ParkingUserRepository extends AbstractRepository<ParkingUser> {

  @Override
  protected Class<ParkingUser> getType() {
    return ParkingUser.class;
  }

  public ParkingUser selectByName(@NotNull String username) {
    return getEntity(Map.of("username", username));
  }

  public List<ParkingUser> getByZone(@NotNull Long zone) {

    Predicate<ParkingUser> isZoneWorker = user ->
      ofNullable(user.getParkingZone()).map(ParkingZone::getId).filter(zone::equals).isPresent();

    return filterList(getEntities(), isZoneWorker);
  }
}
