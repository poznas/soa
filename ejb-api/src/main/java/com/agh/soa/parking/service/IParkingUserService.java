package com.agh.soa.parking.service;

import com.agh.soa.parking.exception.InvalidPasswordException;
import com.agh.soa.parking.model.entity.ParkingUser;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface IParkingUserService extends ISessionContextService {

  List<ParkingUser> getUsers();

  void changeUserPassword(@NotNull String oldPassword, @NotNull String newPassword)
    throws InvalidPasswordException;

  void changeUserPassword(@NotNull Long userId, @NotNull String newPassword);

}
