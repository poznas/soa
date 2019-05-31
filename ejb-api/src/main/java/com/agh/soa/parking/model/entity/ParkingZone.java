package com.agh.soa.parking.model.entity;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

  @OneToMany(fetch = EAGER, mappedBy = "zone")
  private List<ParkingSpace> spaces;
}
