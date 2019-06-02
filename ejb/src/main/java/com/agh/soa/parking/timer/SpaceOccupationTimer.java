package com.agh.soa.parking.timer;

import static java.lang.String.format;
import static java.time.LocalDateTime.now;
import static java.time.temporal.ChronoUnit.MILLIS;
import static java.util.Optional.ofNullable;

import com.agh.soa.parking.model.entity.ParkingSpace;
import com.agh.soa.parking.model.entity.ParkingTicket;
import java.time.LocalDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@RequiredArgsConstructor
public class SpaceOccupationTimer extends TimerTask {

  public static final long STARTER_FREE_TIME = 5;

  protected final Timer timer;
  protected final Supplier<ParkingSpace> spaceSupplier;
  protected final Supplier<ParkingTicket> activeTicketSupplier;
  protected final Runnable workerNotifier;

  protected final ParkingSpace space;

  public void schedule() {

    LocalDateTime fireTime = getFireTime();
    long delay = now().until(fireTime, MILLIS);
    timer.schedule(this, delay);

    log("scheduled timer to fire at %s", fireTime);
  }

  protected LocalDateTime getFireTime() {
    return now().plusSeconds(STARTER_FREE_TIME);
  }

  @Override
  public void run() {

    if (spaceSupplier.get().isOccupied()) {
      log("space is still occupied");

      ofNullable(activeTicketSupplier.get())
        .ifPresentOrElse(this::onActiveTicketExists, workerNotifier);

    } else {
      log("space is no longer occupied");
    }
  }

  protected void onActiveTicketExists(ParkingTicket newTicket) {
    log("starter free occupation time is over but the ticket [%d] was bought", newTicket.getId());
  }

  protected String getLogPrefix() {
    return format("zone [%d] | space[%d] | ", space.getZoneId(), space.getId());
  }

  void log(String format, Object... args) {
    log.info(getLogPrefix() + format(format, args));
  }
}
