package com.agh.soa.parking.dao;

import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.parking.model.ParkingUser;
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
    return getEntity("username", username);
  }
}
