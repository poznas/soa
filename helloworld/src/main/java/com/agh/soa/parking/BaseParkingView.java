package com.agh.soa.parking;

import com.agh.soa.parking.exception.ActiveSessionException;
import com.agh.soa.parking.service.IParkingUserService;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;

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
        ex.printStackTrace();
      }
    }
  }

  @PreDestroy
  public void viewDestroyed() {
    userService.unregisterSession();
  }

  public String logout() throws ServletException {
    userService.unregisterSession();
    getRequest().getSession().invalidate();
    getRequest().logout();
    return "logout";
  }

  private static HttpServletRequest getRequest() {
    return (HttpServletRequest) getExternalContext().getRequest();
  }

  private static ExternalContext getExternalContext() {
    return FacesContext.getCurrentInstance()
      .getExternalContext();
  }

}
