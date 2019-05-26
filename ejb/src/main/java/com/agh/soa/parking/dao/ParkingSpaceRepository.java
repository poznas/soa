package com.agh.soa.parking.dao;

import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.parking.model.ParkingSpace;
import java.util.Map;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

@Stateful
@TransactionManagement(BEAN)
public class ParkingSpaceRepository extends AbstractRepository<ParkingSpace> {

  @Override
  protected Class<ParkingSpace> getType() {
    return ParkingSpace.class;
  }

  public ParkingSpace getSpace(Long zoneId, Long spaceId) {
    return getEntity(Map.of("zoneId", zoneId, "id", spaceId));
  }
}
