package com.agh.soa.lab7;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;

@Log
public abstract class AbstractMessageProducer {

  @Resource(name = "java:/ConnectionFactory")
  ConnectionFactory connectionFactory;

  protected abstract Destination getDestination();

  protected void sendMessage(@NotNull MessageBuilder builder) {
    try (
      var connection = connectionFactory.createConnection();
      var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      var publisher = session.createProducer(getDestination())
    ) {
      connection.start();
      publisher.send(builder.buildMessage(session));
    } catch (JMSException e) {
      log.warning(e.getMessage());
    }
  }

  protected interface MessageBuilder {

    Message buildMessage(Session session) throws JMSException;
  }

}
