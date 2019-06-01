package com.agh.soa.parking.view;

import com.agh.soa.parking.jms.ParkingStateChangeSubscriber;
import com.agh.soa.parking.service.IParkingDashboardSupplier;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
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

  @Inject
  @Push(channel = "refreshZones")
  private PushContext refreshZones;

  @Inject
  ParkingStateChangeSubscriber stateChangeSubscriber;

  @PostConstruct
  public void subscribeForChange() {
    stateChangeSubscriber.subscribe(refreshZones::send);
  }

}
