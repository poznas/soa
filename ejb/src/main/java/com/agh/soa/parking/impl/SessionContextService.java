package com.agh.soa.parking.impl;

import static com.agh.soa.parking.model.ParkingRole.ADMINISTRATOR;
import static java.util.Optional.ofNullable;

import com.agh.soa.parking.dao.ParkingUserRepository;
import com.agh.soa.parking.exception.ActiveSessionException;
import com.agh.soa.parking.model.entity.ParkingUser;
import com.agh.soa.parking.service.ISessionContextService;
import java.util.Optional;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBAccessException;
import javax.ejb.SessionContext;
import javax.validation.constraints.NotNull;

class SessionContextService implements ISessionContextService {

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
    return getCurrentUser().filter(user -> role.equals(user.getRole())).isPresent();
  }

  Optional<ParkingUser> getCurrentUser() {
    return ofNullable(principalName()).map(userRepository::selectByName);
  }

  void assertAdminRole() {
    if (!isCallerInRole(ADMINISTRATOR)) throw new EJBAccessException();
  }

}
