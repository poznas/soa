package com.agh.soa.parking.dao;

import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.parking.model.ParkingZone;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

@Stateful
@TransactionManagement(BEAN)
public class ParkingZoneRepository extends AbstractRepository<ParkingZone> {

  @Override
  protected Class<ParkingZone> getType() {
    return ParkingZone.class;
  }
}
