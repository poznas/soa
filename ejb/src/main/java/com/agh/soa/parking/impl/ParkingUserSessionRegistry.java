package com.agh.soa.parking.impl;

import static java.util.Optional.of;

import com.agh.soa.parking.exception.ActiveSessionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.validation.constraints.NotNull;

@Startup
@Singleton
public class ParkingUserSessionRegistry {

  private Map<String, String> activeSessions = new HashMap<>();


  public void registerSession(@NotNull String username, String sessionId)
    throws ActiveSessionException {

    Optional<String> activeUserSession = of(username).map(activeSessions::get)
      .filter(activeSessionId -> !activeSessionId.equals(sessionId));

    if (activeUserSession.isPresent()) {
        throw new ActiveSessionException(activeUserSession.get());
      }
      activeSessions.put(username, sessionId);
  }

  public void unregisterSession(@NotNull String username) {
    activeSessions.remove(username);
  }

}
