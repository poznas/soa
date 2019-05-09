package com.agh.soa.lab7;

import static com.agh.soa.lab7.JmsPropertyName.READER_ID;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import lombok.extern.java.Log;

@Log
@Stateful
public class LibraryMessageProducer {

  @Resource(name = "java:/ConnectionFactory")
  ConnectionFactory connectionFactory;

  @Resource(name = "java:/queue/AghSoaQueue")
  Queue libraryQueue;

  public void sendMessage(String text)  {
    sendMessage(0, text);
  }

  public void sendMessage(long readerId, String text) {
    try(
      Connection connection = connectionFactory.createConnection();
      Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
      MessageProducer publisher = session.createProducer(libraryQueue)
    ) {
      connection.start();
      TextMessage message = session.createTextMessage(text);
      message.setLongProperty(READER_ID, readerId);
      publisher.send(message);
    } catch (JMSException e) {
      log.warning(e.getMessage());
    }
  }
}
