package com.agh.soa.parking;

import javax.enterprise.context.ApplicationScoped;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import lombok.extern.java.Log;

@Log
@WebListener
@ApplicationScoped
public class ParkingSessionListener implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent event) {
    log.info("Session created: " + event.getSession().getId());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent event) {
    log.info("Session destroyed: " + event.getSession().getId());
  }
}
