package com.agh.soa.parking.jms;

import static com.agh.soa.parking.JmsPropertyName.SPACE_ID;
import static com.agh.soa.parking.JmsPropertyName.WORKER_ID;
import static java.lang.String.format;
import static java.util.Objects.nonNull;

import java.util.function.Consumer;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;

@Log
public abstract class SessionTopicSubscriber implements MessageListener {

  @Resource(name = "java:/ConnectionFactory")
  ConnectionFactory connectionFactory;

  private Connection connection;
  private Session session;
  private MessageConsumer jmsConsumer;

  private Consumer<String> messageConsumer;

  abstract Topic getTopic();

  public void subscribe(@NotNull Consumer<String> runnable) {
    subscribe(null, runnable);
  }

  public void subscribe(String username, @NotNull Consumer<String> runnable) {

    close();
    this.messageConsumer = runnable;

    try {
      jmsConsumer = createTopicConsumer(username);
      jmsConsumer.setMessageListener(this);
      connection.start();

    } catch (JMSException e) {
      log.warning(e.getMessage());
    }
  }

  private MessageConsumer createTopicConsumer(String username) throws JMSException {

    connection = connectionFactory.createConnection();
    session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    String selector = nonNull(username) ? format("%s = '%s'", WORKER_ID, username) : null;

    return session.createConsumer(getTopic(), selector);
  }

  @Override
  public void onMessage(Message message) {
    try {
      String text = ((TextMessage) message).getText();
      text = format("%s, (space ID: %s)", text, message.getStringProperty(SPACE_ID));

      log.info("Received message: " + text);
      messageConsumer.accept(text);
    } catch (JMSException e) {
      log.warning(e.getMessage());
    }
  }

  @PreDestroy
  public void close() {
    close(jmsConsumer);
    close(session);
    close(connection);
  }

  private void close(AutoCloseable closeable) {
    if (nonNull(closeable)) {
      try {
        closeable.close();
      } catch (Exception e) {
        log.warning(e.getMessage());
      }
    }
  }

}
