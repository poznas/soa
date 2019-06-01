package com.agh.soa.parking.jms;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.enterprise.context.SessionScoped;
import javax.jms.Topic;
import lombok.Getter;

@Getter
@SessionScoped
public class ParkingStateChangeSubscriber extends SessionTopicSubscriber implements Serializable {

  @Resource(name = "java:/topic/ParkingRefreshTopic")
  Topic topic;
}
