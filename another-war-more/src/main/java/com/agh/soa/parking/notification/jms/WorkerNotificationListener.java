package com.agh.soa.parking.notification.jms;

import com.agh.soa.parking.notification.impl.WorkerNotificationService;
import com.agh.soa.parking.notification.model.WorkerNotification;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import lombok.extern.java.Log;

@Log
@MessageDriven(
  name = "WorkerNotificationListener",
  activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "ParkingWorkerNotificationQueue"),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
  }
)
public class WorkerNotificationListener implements MessageListener {

  @Inject
  WorkerNotificationService notificationService;

  @Override
  public void onMessage(Message message) {
    try {
      log.info("Message received: " + message.getJMSMessageID());

      notificationService.handleNotification(WorkerNotification.of(message));

    } catch (JMSException e) {
      log.warning(e.getMessage());
    }
  }
}
