package com.agh.soa.parking.jms;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.jms.Topic;
import lombok.Getter;

@Getter
@SessionScoped
public class WorkerNotificationSubscriber extends SessionTopicSubscriber implements Serializable {

  @Resource(name = "java:/topic/ParkingWorkerNotificationTopic")
  Topic topic;
}
