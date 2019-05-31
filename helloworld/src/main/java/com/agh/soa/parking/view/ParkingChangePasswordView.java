package com.agh.soa.parking.view;

import com.agh.soa.parking.exception.InvalidPasswordException;
import com.agh.soa.parking.service.IParkingUserService;
import javax.ejb.EJB;
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

  @EJB(lookup = "java:global/ejb/ParkingUserService")
  IParkingUserService service;

  @Setter
  private String oldPassword;
  @Setter
  private String newPassword;
  @Setter
  private Long pickedUserId;

  public void changeYourPassword() {
    try {
      service.changeUserPassword(oldPassword, newPassword);
    } catch (InvalidPasswordException ex) {
      showError(ex.getMessage());
    }
  }

  public void changeUserPassword() {
    service.changeUserPassword(pickedUserId, newPassword);
  }
}
