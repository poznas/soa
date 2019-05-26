package com.agh.soa.parking.model;

import static javax.persistence.GenerationType.IDENTITY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpace implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private boolean occupied;

  @ManyToOne
  @JsonIgnore
  private ParkingZone zone;

  @Column(name="zone_id", updatable=false, insertable=false)
  private Long zoneId;

}
