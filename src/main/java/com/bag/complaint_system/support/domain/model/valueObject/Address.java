package com.bag.complaint_system.support.domain.model.valueObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Address {

  private final String street;
  private final District district;

  public Address(String street, District district) {
    if (street == null || street.trim().isEmpty()) {
      throw new IllegalArgumentException("Street cannot be empty.");
    }
    if (street.length() > 200) {
      throw new IllegalArgumentException("Street cannot exceed 200 characters.");
    }
    if (district == null) {
      throw new IllegalArgumentException("District cannot be null.");
    }

    this.street = normalizeStreet(street);
    this.district = district;
  }

  public String getFullAddress() {
    return street + ", " + district.getDisplayName();
  }

  private String normalizeStreet(String street) {
    String normalized = street.trim().toLowerCase();
    String[] words = normalized.split("\\s+");
    StringBuilder result = new StringBuilder();
    for (String w : words) {
      result.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
    }
    return result.toString().trim();
  }
}
