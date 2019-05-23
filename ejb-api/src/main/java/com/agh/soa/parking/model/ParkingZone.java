package com.agh.soa.parking.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ParkingZone implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @Column(unique = true)
  private String name;
}
