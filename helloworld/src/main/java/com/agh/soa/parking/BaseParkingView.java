package com.agh.soa.parking;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

import com.agh.soa.parking.exception.ActiveSessionException;
import com.agh.soa.parking.service.IParkingUserService;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
public class BaseParkingView implements Serializable {

  @EJB(lookup = "java:global/ejb/ParkingUserService")
  IParkingUserService userService;

  @PostConstruct
  public void sessionCreated() {
    try {
      userService.registerSession(getRequest().getSession().getId());
    } catch (ActiveSessionException e) {
      try {
        getExternalContext().redirect("/parking/public/loginError.xhtml?msg=" + e.getMessage());
      } catch (IOException ex) {
        log.warning(ex.getMessage());
      }
    }
  }

  @PreDestroy
  public void viewDestroyed() {
    userService.unregisterSession();
  }

  public String logout() {
    userService.unregisterSession();
    getRequest().getSession().invalidate();
    return "logout";
  }

  static void showError(String message) {
    facesContext().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Error!", message));
  }

  private static HttpServletRequest getRequest() {
    return (HttpServletRequest) getExternalContext().getRequest();
  }

  private static ExternalContext getExternalContext() {
    return facesContext().getExternalContext();
  }

  private static FacesContext facesContext() {
    return FacesContext.getCurrentInstance();
  }

}
