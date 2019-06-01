package com.agh.soa.parking.jms;

import static com.agh.soa.parking.JmsPropertyName.SPACE_ID;
import static com.agh.soa.parking.JmsPropertyName.ZONE_ID;
import static java.lang.String.format;

import com.agh.soa.lab7.AbstractMessageProducer;
import com.agh.soa.parking.model.entity.ParkingSpace;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Topic;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
@Stateless
public class ParkingStateChangeNotifier extends AbstractMessageProducer {

  @Resource(name = "java:/topic/ParkingRefreshTopic")
  Topic destination;

  public void parkingSpaceStateChanged(ParkingSpace space) {
    log.info(format("zone [%d] space [%d] state changed", space.getZoneId(), space.getId()));

    sendMessage(session -> {
      var msg = session.createTextMessage("Parking space state changed");

      msg.setLongProperty(ZONE_ID, space.getZoneId());
      msg.setLongProperty(SPACE_ID, space.getId());

      return msg;
    });
  }

}
