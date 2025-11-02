package com.bag.complaint_system.shared.domain.exception;

public class UserAlreadyExistsException extends DomainException {

  public UserAlreadyExistsException(String message) {
    super(message);
  }
}
