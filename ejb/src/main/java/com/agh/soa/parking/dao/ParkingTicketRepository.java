package com.agh.soa.parking.dao;

import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.parking.model.ParkingTicket;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

@Stateful
@TransactionManagement(BEAN)
public class ParkingTicketRepository extends AbstractRepository<ParkingTicket> {

  @Override
  protected Class<ParkingTicket> getType() {
    return ParkingTicket.class;
  }
}
