package com.agh.soa.parking.exception;

public class InvalidPasswordException extends RuntimeException implements Runnable {

  public static final InvalidPasswordException INVALID_PASSWORD
    = new InvalidPasswordException("Provided current password does not match stored hash");

  private InvalidPasswordException(String message) {
    super(message);
  }

  @Override
  public void run() {
    throw this;
  }
}
