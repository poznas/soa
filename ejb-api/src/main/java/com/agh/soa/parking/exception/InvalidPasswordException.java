package com.agh.soa.parking.exception;

public class InvalidPasswordException extends Exception {

  public static final InvalidPasswordException INVALID_PASSWORD
    = new InvalidPasswordException("Provided current password does not match stored hash");

  private InvalidPasswordException(String message) {
    super(message);
  }

}
