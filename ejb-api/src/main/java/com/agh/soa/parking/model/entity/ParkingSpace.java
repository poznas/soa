package com.agh.soa.parking.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.xml.bind.annotation.XmlAccessType.FIELD;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(FIELD)
public class ParkingSpace implements Serializable {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private boolean occupied;
  private LocalDateTime occupationStartTime;

  @ManyToOne
  @JsonIgnore
  @XmlTransient
  private ParkingZone zone;

  @Column(name="zone_id", updatable=false, insertable=false)
  private Long zoneId;

}
