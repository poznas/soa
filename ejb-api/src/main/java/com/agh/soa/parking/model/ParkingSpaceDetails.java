package com.agh.soa.parking.model;

import com.agh.soa.parking.model.entity.ParkingSpace;
import com.agh.soa.parking.model.entity.ParkingTicket;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ParkingSpaceDetails extends ParkingSpace {

  private List<ParkingTicket> recentTickets;

  private boolean occupationAuthorized;

}
