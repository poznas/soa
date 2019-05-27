package com.agh.soa.parking.timer;

import static java.lang.String.format;

import com.agh.soa.parking.model.ParkingSpace;
import com.agh.soa.parking.model.ParkingTicket;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.function.Supplier;
import lombok.Builder;

public class SpaceTicketTimer extends SpaceOccupationTimer {

  private static final long EXPIRE_DELAY_SECONDS = 5;

  private final ParkingTicket ticket;

  @Builder
  SpaceTicketTimer(Timer timer, Supplier<ParkingSpace> spaceSupplier,
                   Supplier<ParkingTicket> activeTicketSupplier, Runnable workerNotifier,
                   ParkingSpace space, ParkingTicket ticket) {
    super(timer, spaceSupplier, activeTicketSupplier, workerNotifier, space);
    this.ticket = ticket;
  }

  @Override
  protected LocalDateTime getFireTime() {
    return ticket.getExpireTime().plusSeconds(EXPIRE_DELAY_SECONDS);
  }

  @Override
  protected void onActiveTicketExists(ParkingTicket newTicket) {
    log("allowed parking space occupation time was extended by purchasing a new ticket [%d]",
      newTicket.getId());
  }

  @Override
  protected String getLogPrefix() {
    return super.getLogPrefix() + format("ticket [%d] | ", ticket.getId());
  }
}
