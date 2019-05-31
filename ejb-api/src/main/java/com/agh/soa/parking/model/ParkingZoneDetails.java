package com.agh.soa.parking.model;

import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ParkingZoneDetails implements Serializable {

  private Long id;
  private String name;

  private List<ParkingSpaceDetails> spaces;

}
