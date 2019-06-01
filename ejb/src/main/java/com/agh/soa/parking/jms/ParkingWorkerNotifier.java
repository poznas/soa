package com.agh.soa.parking.jms;

import static com.agh.soa.parking.JmsPropertyName.SPACE_ID;
import static com.agh.soa.parking.JmsPropertyName.WORKER_ID;
import static com.agh.soa.parking.JmsPropertyName.ZONE_ID;
import static java.lang.String.format;

import com.agh.soa.lab7.AbstractMessageProducer;
import com.agh.soa.parking.dao.ParkingUserRepository;
import com.agh.soa.parking.model.entity.ParkingSpace;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.Queue;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
@Stateless
public class ParkingWorkerNotifier extends AbstractMessageProducer {

  @Resource(name = "java:/queue/ParkingWorkerNotificationQueue")
  Queue destination;

  @EJB
  ParkingUserRepository userRepository;


  public void unauthorizedSpaceOccupation(ParkingSpace space) {

    log.info(format("Notifying zone [%d] workers about unauthorized space [%d] occupation",
      space.getZoneId(), space.getId()));

    userRepository.getByZone(space.getZoneId())
      .forEach(worker ->
        sendMessage(session -> {
          var msg = session.createTextMessage("Unauthorized space occupation");

          msg.setStringProperty(WORKER_ID, worker.getUsername());
          msg.setLongProperty(ZONE_ID, space.getZoneId());
          msg.setLongProperty(SPACE_ID, space.getId());

          return msg;
        })
      );

  }
}
