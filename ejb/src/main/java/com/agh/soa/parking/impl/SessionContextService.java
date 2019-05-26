package com.agh.soa.parking.impl;

import static com.agh.soa.parking.model.ParkingRole.ADMINISTRATOR;
import static java.util.Optional.ofNullable;

import com.agh.soa.parking.dao.ParkingUserRepository;
import com.agh.soa.parking.exception.ActiveSessionException;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.validation.constraints.NotNull;

class SessionContextService {

  @Resource
  SessionContext context;
  @EJB
  ParkingUserRepository userRepository;
  @EJB
  ParkingUserSessionRegistry sessionStore;

  public void registerSession(@NotNull String sessionId) throws ActiveSessionException {
    sessionStore.registerSession(principalName(), sessionId);
  }

  public void unregisterSession() {
    sessionStore.unregisterSession(principalName());
  }

  String principalName() {
    return context.getCallerPrincipal().getName();
  }

  // https://access.redhat.com/solutions/138613 >:^c
  boolean isCallerInRole(String role) {
    return ofNullable(principalName()).map(userRepository::selectByName)
      .filter(user -> role.equals(user.getRole())).isPresent();
  }

  void assertAdminRole() {
    if (!isCallerInRole(ADMINISTRATOR)) throw new EJBAccessException();
  }

}
