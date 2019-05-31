package com.agh.soa.parking.impl;

import static com.agh.soa.parking.model.ParkingRole.ADMINISTRATOR;
import static com.agh.soa.parking.model.ParkingRole.WORKER;
import static com.agh.soa.utils.CollectionUtils.filterList;
import static com.agh.soa.utils.CollectionUtils.mapList;
import static com.agh.soa.utils.ObjectUtils.copyProperties;
import static java.time.LocalDateTime.now;
import static java.util.Collections.emptySet;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.groupingBy;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;

import com.agh.soa.parking.dao.ParkingTicketRepository;
import com.agh.soa.parking.dao.ParkingZoneRepository;
import com.agh.soa.parking.model.ParkingSpaceDetails;
import com.agh.soa.parking.model.ParkingZoneDetails;
import com.agh.soa.parking.model.entity.ParkingSpace;
import com.agh.soa.parking.model.entity.ParkingTicket;
import com.agh.soa.parking.model.entity.ParkingUser;
import com.agh.soa.parking.model.entity.ParkingZone;
import com.agh.soa.parking.service.IParkingDashboardSupplier;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import javax.annotation.security.DeclareRoles;
import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;
import org.jboss.ejb3.annotation.SecurityDomain;

@Log
@Stateful
@SecurityDomain("parkingApp")
@Remote(IParkingDashboardSupplier.class)
@DeclareRoles({ADMINISTRATOR, WORKER})
public class ParkingDashboardSupplier extends SessionContextService implements
  IParkingDashboardSupplier {

  @EJB
  ParkingZoneRepository zoneRepository;
  @EJB
  ParkingTicketRepository ticketRepository;

  @Override
  public List<ParkingZoneDetails> getDashboardZones() {

    var zones = isCallerInRole(ADMINISTRATOR) ?
      zoneRepository.getEntities()
      : getCurrentUser().map(ParkingUser::getParkingZone)
        .map(Collections::singleton).orElse(emptySet());

    var allRecentTickets = ticketRepository.getRecentTickets().stream()
      .sorted(comparing(ParkingTicket::getExpireTime))
      .collect(groupingBy(ticket -> ticket.getSpace().getId()));

    Function<ParkingSpace, ParkingSpaceDetails> decorateSpace = space -> {
      var details = new ParkingSpaceDetails();
      copyProperties(details, space);
      details.setRecentTickets(allRecentTickets.get(space.getId()));
      setOccupationAuthorizedStatus(details);
      return details;
    };

    Function<ParkingZone, ParkingZoneDetails> detailedZone = zone ->
      ParkingZoneDetails.of(zone.getId(), zone.getName(), mapList(zone.getSpaces(), decorateSpace));

    return mapList(zones, detailedZone);
  }

  private void setOccupationAuthorizedStatus(@NotNull ParkingSpaceDetails space) {
    var activeTickets = filterList(space.getRecentTickets(),
      ticket -> ticket.getExpireTime().isAfter(now()));

    space.setOccupationAuthorized(isNotEmpty(activeTickets));
  }

}
