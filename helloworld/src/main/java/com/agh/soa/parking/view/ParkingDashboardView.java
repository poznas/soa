package com.agh.soa.parking.view;

import com.agh.soa.parking.service.IParkingDashboardSupplier;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
@SessionScoped
@Named("dashboard")
public class ParkingDashboardView extends BaseParkingView {

  @EJB(lookup = "java:global/ejb/ParkingDashboardSupplier")
  IParkingDashboardSupplier service;

}
