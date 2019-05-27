package com.agh.soa.parking.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParkingTicket {

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;
  private LocalDateTime purchaseTime;
  private TicketType type;
  private LocalDateTime expireTime;

  @ManyToOne
  private ParkingSpace space;

}
