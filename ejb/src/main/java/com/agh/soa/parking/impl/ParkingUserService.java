package com.agh.soa.parking.impl;

import static com.agh.soa.parking.exception.InvalidPasswordException.INVALID_PASSWORD;
import static com.agh.soa.parking.model.ParkingRole.ADMINISTRATOR;
import static com.agh.soa.parking.model.ParkingRole.WORKER;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

import com.agh.soa.parking.exception.InvalidPasswordException;
import com.agh.soa.parking.model.ParkingUser;
import com.agh.soa.parking.service.IParkingUserService;
import java.util.List;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.validation.constraints.NotNull;
import lombok.extern.java.Log;
import org.jboss.ejb3.annotation.SecurityDomain;

@Log
@Stateful
@SecurityDomain("parkingApp")
@Remote(IParkingUserService.class)
@DeclareRoles({ADMINISTRATOR, WORKER})
public class ParkingUserService extends SessionContextService implements IParkingUserService {

  @Override
  @PermitAll
  public List<ParkingUser> getUsers() {
    return isCallerInRole(ADMINISTRATOR) ? userRepository.getEntities() : emptyList();
  }

  @Override
  @PermitAll
  public void changeUserPassword(@NotNull String oldPassword, @NotNull String newPassword)
    throws InvalidPasswordException {
    var user = of(principalName()).map(userRepository::selectByName)
      .filter(u -> u.getPassword().equals(sha256Hex(oldPassword)));

    if (user.isPresent()) {
      changeUserPassword(user.get(), newPassword);
    } else {
      throw INVALID_PASSWORD;
    }
  }

  @Override
  @PermitAll
  public void changeUserPassword(@NotNull Long userId, @NotNull String newPassword) {
    assertAdminRole();
    of(userId).map(userRepository::getEntity)
      .ifPresent(user -> changeUserPassword(user, newPassword));
  }

  private void changeUserPassword(@NotNull ParkingUser user, @NotNull String newPassword) {

    user.setPassword(sha256Hex(newPassword));
    userRepository.merge(user);
  }

}
