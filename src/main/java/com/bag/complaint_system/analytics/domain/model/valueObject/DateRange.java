package com.bag.complaint_system.analytics.domain.model.valueObject;

import lombok.Getter;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class DateRange {
  private final LocalDateTime startDate;
  private final LocalDateTime endDate;

  public DateRange(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate == null || endDate == null) {
      throw new IllegalArgumentException("Start date and end date cannot be null");
    }
    if (startDate.isAfter(endDate)) {
      throw new IllegalArgumentException("Start date cannot be after end date");
    }
    this.startDate = startDate;
    this.endDate = endDate;
  }

  public static DateRange currentMonth() {
    LocalDate now = LocalDate.now();
    LocalDate firstDay = now.withDayOfMonth(1);
    LocalDate lastDay = now.withDayOfMonth(now.lengthOfMonth());

    return new DateRange(firstDay.atStartOfDay(), lastDay.atTime(23, 59, 59));
  }

  public static DateRange currentYear() {
    LocalDate now = LocalDate.now();
    LocalDate firstDay = now.withDayOfYear(1);
    LocalDate lastDay = now.withDayOfYear(now.lengthOfYear());

    return new DateRange(firstDay.atStartOfDay(), lastDay.atTime(23, 59, 59));
  }

  public static DateRange lastNDays(int days) {
    if (days <= 0) {
      throw new IllegalArgumentException("Number of days must be greater than 0");
    }

    LocalDateTime now = LocalDateTime.now();
    LocalDateTime startDate = now.minusDays(days);

    return new DateRange(startDate, now);
  }

  public static DateRange between(LocalDate startDate, LocalDate endDate) {
    return new DateRange(startDate.atStartOfDay(), endDate.atTime(23, 59, 59));
  }
}
