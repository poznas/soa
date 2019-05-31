package com.agh.soa.parking.dao;

import static com.agh.soa.utils.CollectionUtils.filterList;
import static java.time.LocalDateTime.now;
import static javax.ejb.TransactionManagementType.BEAN;

import com.agh.soa.lab5.AbstractRepository;
import com.agh.soa.parking.model.entity.ParkingTicket;
import java.util.List;
import java.util.function.Predicate;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;

@Stateful
@TransactionManagement(BEAN)
public class ParkingTicketRepository extends AbstractRepository<ParkingTicket> {

  @Override
  protected Class<ParkingTicket> getType() {
    return ParkingTicket.class;
  }

  public ParkingTicket getActiveTicket(Long zoneId, Long spaceId) {
    return getEntities().stream()
      .filter(ticket -> ticket.getSpace().getZoneId().equals(zoneId))
      .filter(ticket -> ticket.getSpace().getId().equals(spaceId))
      .filter(ticket -> ticket.getExpireTime().isAfter(now()))
      .findFirst().orElse(null);
  }

  public List<ParkingTicket> getRecentTickets() {
    return filterList(getEntities(), isRecent);
  }

  private static Predicate<ParkingTicket> isRecent = ticket ->
    ticket.getExpireTime().isAfter(now().minusMinutes(10));
}
