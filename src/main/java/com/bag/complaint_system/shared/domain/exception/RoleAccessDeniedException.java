package com.bag.complaint_system.shared.domain.exception;

public class RoleAccessDeniedException extends DomainException {
  public RoleAccessDeniedException(String message) {
    super(message);
  }
}
