package com.agh.soa.parking.notification.model;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@EqualsAndHashCode(of = "messageId")
public class WorkerNotification {

  private String messageId;
  private String username;
  private Long zoneId;
  private Long spaceId;
  private String messageBody;
  private long timestamp;

  public static WorkerNotification of(Message message) throws JMSException {

    return WorkerNotification.builder()
      .messageId(message.getJMSMessageID())
      .messageBody(((TextMessage) message).getText())
      .username(message.getStringProperty("workerId"))
      .spaceId(message.getLongProperty("spaceId"))
      .zoneId(message.getLongProperty("zoneId"))
      .timestamp(message.getJMSTimestamp())
      .build();
  }

}
