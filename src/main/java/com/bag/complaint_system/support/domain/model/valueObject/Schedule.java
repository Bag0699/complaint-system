package com.bag.complaint_system.support.domain.model.valueObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Schedule {

  private String value;

  public Schedule(String value) {
    this.value = validateAndNormalize(value);
  }

  private String validateAndNormalize(String schedule) {
    if (schedule == null || schedule.trim().isEmpty()) {
      throw new IllegalArgumentException("Schedule cannot be empty");
    }

    String normalized = schedule.trim().toLowerCase();

    if (normalized.length() > 200) {
      throw new IllegalArgumentException("Schedule cannot exceed 200 characters");
    }
    return normalized;
  }
}
