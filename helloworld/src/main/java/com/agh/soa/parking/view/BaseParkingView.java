package com.agh.soa.parking.view;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;

import com.agh.soa.parking.exception.ActiveSessionException;
import com.agh.soa.parking.jms.WorkerNotificationSubscriber;
import com.agh.soa.parking.service.ISessionContextService;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.java.Log;

@Log
@Getter
public abstract class BaseParkingView implements Serializable {

  @Inject
  WorkerNotificationSubscriber workerNotificationSubscriber;

  @Inject
  @Push(channel = "notifier")
  private PushContext notifier;

  abstract ISessionContextService getService();

  @PostConstruct
  public void sessionCreated() {
    try {
      getService().registerSession(getRequest().getSession().getId());
      workerNotificationSubscriber.subscribe(getUsername(), msg -> {
        delay();
        notifier.send(msg);
      });
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
    getService().unregisterSession();
  }

  public String logout() {
    getService().unregisterSession();
    getRequest().getSession().invalidate();
    return "logout";
  }

  static void showError(String message) {
    facesContext().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Error!", message));
  }

  private static String getUsername() {
    return getExternalContext().getUserPrincipal().getName();
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

  /**
   * Couldn't find a way to rerender zones dataGrid,
   * used window.location.reload() instead.
   *
   * page refresh terminates browser popup alerts
   * -> give refresh time to complete then popup alerts
   */
  private static void delay() {
    try {
      MILLISECONDS.sleep(1500);
    } catch (Exception e) {
      log.warning(e.getMessage());
    }
  }

}
