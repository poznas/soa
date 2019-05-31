package com.agh.soa.parking.service;

import com.agh.soa.parking.model.ParkingZoneDetails;
import java.util.List;

public interface IParkingDashboardSupplier extends ISessionContextService{

  List<ParkingZoneDetails> getDashboardZones();

}
