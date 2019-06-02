package com.agh.soa.parking.wsdl;

import static javax.jws.soap.SOAPBinding.Style.RPC;

import com.agh.soa.parking.dao.ParkingTicketRepository;
import com.agh.soa.parking.dao.ParkingZoneRepository;
import com.agh.soa.parking.model.entity.ParkingTicket;
import com.agh.soa.parking.model.entity.ParkingZone;
import java.util.List;
import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = RPC)
public class ParkingMonitorWebService {

  @Inject
  ParkingZoneRepository zoneRepository;
  @Inject
  ParkingTicketRepository ticketRepository;

  @WebMethod
  public List<ParkingZone> getParkingZones() {
    return zoneRepository.getEntities();
  }

  @WebMethod
  public List<ParkingTicket> getParkingTickets() {
    return ticketRepository.getEntities();
  }

}
