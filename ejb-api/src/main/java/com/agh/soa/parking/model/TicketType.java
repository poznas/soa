package com.agh.soa.parking.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TicketType {
  SEC_10(10),
  SEC_20(20),
  MIN_1(60),
  MIN_10(600),
  ;

  private final long expireSeconds;
}
