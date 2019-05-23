package com.agh.soa.parking;

import com.agh.soa.parking.exception.InvalidPasswordException;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

@Log
@Getter
@SessionScoped
@Named("passView")
public class ParkingChangePasswordView extends BaseParkingView {

  @Setter
  private String oldPassword;
  @Setter
  private String newPassword;
  @Setter
  private Long pickedUserId;

  private String errorMessage;

  public void changeYourPassword() {
    try {
      userService.changeUserPassword(oldPassword, newPassword);
    } catch (InvalidPasswordException ex) {
      errorMessage = ex.getMessage();
    }
  }

  public void changeUserPassword() {
    userService.changeUserPassword(pickedUserId, newPassword);
  }
}
