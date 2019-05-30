package com.agh.soa.parking.notification.impl;

import static java.util.Optional.of;
import static javax.ejb.LockType.READ;
import static javax.ejb.LockType.WRITE;

import com.agh.soa.parking.notification.model.WorkerNotification;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.ejb.Lock;
import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotNull;

@ApplicationScoped
public class WorkerNotificationService {

  private Map<String, Set<WorkerNotification>> notifications = new HashMap<>();

  @Lock(READ)
  public Set<WorkerNotification> pullUserNotifications(String username) {
    return notifications.remove(username);
  }

  @Lock(WRITE)
  private void putUserNotification(@NotNull WorkerNotification notification) {

    var userNotifications = of(notification).map(WorkerNotification::getUsername)
      .map(notifications::get).orElse(new HashSet<>());

    userNotifications.add(notification);

    notifications.put(notification.getUsername(), userNotifications);
  }

  public void handleNotification(@NotNull WorkerNotification notification) {
    putUserNotification(notification);

    //TODO: forward to ParkingWorkerNotificationTopic
  }

}
