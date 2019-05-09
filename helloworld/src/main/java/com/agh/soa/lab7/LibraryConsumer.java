package com.agh.soa.lab7;

import javax.jms.JMSException;
import javax.jms.Message;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
@EqualsAndHashCode(of = "readerId")
public class LibraryConsumer {

  @Getter
  private final Long readerId;

  private final MessagePredicate shouldConsume;

  private final MessageConsumer consume;

  void consume(Message message) {
    try {
      consume.consume(message);
    } catch (JMSException e) {
      log.warning(e.getMessage());
    }
  }

  boolean shouldConsume(Message message) {
    try {
      return shouldConsume.test(message);
    } catch (JMSException e) {
      log.warning(e.getMessage());
      return false;
    }
  }

  public interface MessageConsumer {
    void consume(Message message) throws JMSException;
  }

  public interface MessagePredicate {
    boolean test(Message message) throws JMSException;
  }
}
