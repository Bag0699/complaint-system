package com.bag.complaint_system.identity.domain.model.valueObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.regex.Pattern;

@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Phone implements Serializable {

    private static final Pattern PHONE_PATTERN =
            Pattern.compile("^(\\+\\d{1,3}\\s?)?(\\(\\d{1,4}\\)\\s?)?\\d{6,12}$");

    private String value;

    public Phone(String value) {
        this.value = validateAndNormalize(value);
    }

    private String validateAndNormalize(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty");
        }

        String normalized = phone.trim().replaceAll("\\s+", "");

        if (!PHONE_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Invalid phone number format: " + phone);
        }

        if (normalized.length() > 15) {
            throw new IllegalArgumentException("Phone number cannot exceed 15 digits");
        }

        return normalized;
    }
}
