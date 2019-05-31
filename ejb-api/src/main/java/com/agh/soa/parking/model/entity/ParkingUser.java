package com.agh.soa.parking.model.entity;

import static javax.persistence.GenerationType.IDENTITY;

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
public class ParkingUser implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  @Column(unique = true)
  private String username;
  private String password;
  private String role;

  @ManyToOne
  private ParkingZone parkingZone;

}
