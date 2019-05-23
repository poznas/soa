package com.agh.soa.parking.exception;

public class ActiveSessionException extends Exception {

  public ActiveSessionException(String sessionId) {
    super("Active user session exists: " + sessionId);
  }
}
