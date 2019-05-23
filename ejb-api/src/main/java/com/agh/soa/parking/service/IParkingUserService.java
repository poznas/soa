package com.agh.soa.parking.service;

import com.agh.soa.parking.exception.ActiveSessionException;
import com.agh.soa.parking.model.ParkingUser;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface IParkingUserService {

  void registerSession(@NotNull String sessionId) throws ActiveSessionException;

  void unregisterSession();

  List<ParkingUser> getUsers();

  void changeUserPassword(@NotNull String oldPassword, @NotNull String newPassword);

  void changeUserPassword(@NotNull Long userId, @NotNull String newPassword);

}
