package com.circle_devs.zdanovskih.handler;

/**
 * cases when the required data is not found
 */
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }

}
