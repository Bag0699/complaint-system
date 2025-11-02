package com.bag.complaint_system.shared.util;

import lombok.Getter;

@Getter
public enum ErrorCatalog {
  AGGRESSOR_NOT_FOUND("ERR_AGR_001", "Aggressor not found"),
  INVALID_AGGRESSOR("ERR_AGR_002", "Aggressor parameters is invalid"),

  COMPLAINT_NOT_FOUND("ERR_CPT_001", "Complaint not found"),
  INVALID_COMPLAINT("ERR_CPT_002", "Complaint parameters is invalid"),
  INVALID_COMPLAINT_STATUS("ERR_CPT_003", "The complaint status provided is not valid."),

  EVIDENCE_NOT_FOUND("ERR_EVD_001", "Evidence not found"),
  INVALID_EVIDENCE("ERR_EVD_002", "Evidence parameters is invalid"),

  USER_NOT_FOUND("ERR_USR_001", "User not found"),
  INVALID_USER("ERR_USR_002", "User parameters is invalid"),

  SUPPORT_CENTER_NOT_FOUND("ERR_SPC_001", "Support center not found"),
  INVALID_SUPPORT_CENTER("ERR_SPC_002", "Support center parameters is invalid"),
  INVALID_SUPPORT_CENTER_DISTRICT("ERR_SPC_003", "Support center district is invalid"),

  EMAIL_NOT_FOUND("ERR_EML_001", "Email not found"),
  EMAIL_ALREADY_EXISTS("ERR_EML_002", "Email already exists"),

  VICTIM_NOT_FOUND("ERR_VCT_001", "Victim not found"),

  INVALID_ARGUMENT("ERR_ARG_001", "Invalid argument"),

  UNAUTHENTICATED("ERR_AUTH_001", "User is not authenticated"),
  AUTH_FAILED("ERR_AUTH_002", "Invalid credentials"),
  INSUFFICIENT_ROLE("ERR_AUTH_003", "The user is not a victim required for this action"),
  INSUFFICIENT_ADMIN_ROLE("ERR_AUTH_004", "The user is not an admin required for this action"),
  INSUFFICIENT_ACCESS_TO_RESOURCE("ERR_AUTH_006", "You can only view your own complaints"),

  GENERIC_ERROR("ERR_GEN_001", "Un error inesperado ocurrió.");

  private final String code;
  private final String message;

  ErrorCatalog(String code, String message) {
    this.code = code;
    this.message = message;
  }
}
