package com.agh.soa.lab7;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;

@Log
public abstract class AbstractMessageProducer {

  protected abstract ConnectionFactory getConnectionFactory();

  protected abstract Queue getQueue();

  protected void sendMessage(@NotNull MessageBuilder builder) {
    try (
      var connection = getConnectionFactory().createConnection();
      var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      var publisher = session.createProducer(getQueue())
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
