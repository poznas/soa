package com.agh.soa.lab7;

import static com.agh.soa.lab7.JmsPropertyName.READER_ID;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
@Stateful
public class LibraryMessageProducer extends AbstractMessageProducer {

  @Resource(name = "java:/ConnectionFactory")
  ConnectionFactory connectionFactory;

  @Resource(name = "java:/queue/AghSoaQueue")
  Queue queue;


  public void sendMessage(String text)  {
    sendMessage(0, text);
  }

  public void sendMessage(long readerId, String text) {

    sendMessage(session -> {
      var message = session.createTextMessage(text);
      message.setLongProperty(READER_ID, readerId);
      return message;
    });
  }
}
