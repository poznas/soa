package com.agh.soa.parking.notification.controller;

import static com.agh.soa.parking.notification.controller.NotificationController.CONTEXT;

import com.agh.soa.parking.notification.impl.WorkerNotificationService;
import com.agh.soa.parking.notification.model.WorkerNotification;
import io.swagger.annotations.Api;
import java.util.Set;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import lombok.extern.java.Log;

@Log
@Path(CONTEXT)
@Api("Parking Notifications")
@RequestScoped
public class NotificationController {

  static final String CONTEXT = "notifications";

  @Context
  SecurityContext securityContext;

  @Inject
  WorkerNotificationService service;

  @GET
  public Set<WorkerNotification> pull() {
    return service.pullUserNotifications(securityContext.getUserPrincipal().getName());
  }

}
