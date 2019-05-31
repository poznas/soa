package com.agh.soa.parking.impl.crud;

import com.agh.soa.lab8.service.IRestCrudService;
import com.agh.soa.parking.dao.ParkingZoneRepository;
import com.agh.soa.parking.model.entity.ParkingZone;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import lombok.Getter;

@RequestScoped
public class ParkingZoneService implements IRestCrudService<ParkingZone> {

  @EJB
  @Getter
  ParkingZoneRepository repository;

}
