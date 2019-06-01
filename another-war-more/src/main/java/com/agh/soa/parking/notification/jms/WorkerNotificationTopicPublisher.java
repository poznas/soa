package com.agh.soa.parking.notification.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.Topic;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;

@Log
@Stateless
public class WorkerNotificationTopicPublisher {

  @Resource(name = "java:/ConnectionFactory")
  ConnectionFactory connectionFactory;

  @Resource(name = "java:/topic/ParkingWorkerNotificationTopic")
  Topic destination;

  public void sendMessage(@NotNull Message message) {
    try (
      var connection = connectionFactory.createConnection();
      var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      var publisher = session.createProducer(destination)
    ) {
      connection.start();
      log.info("publish message for " + message.getStringProperty("workerId"));
      publisher.send(message);
    } catch (JMSException e) {
      log.warning(e.getMessage());
    }
  }

}
