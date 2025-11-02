package com.bag.complaint_system.identity.application.dto.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileRequest {
    @NotBlank(message = "The field firstname cannot be blank or null.")
    @Size(min = 2, max = 100, message = "First name must be between 2 and 100 characters")
    private String firstName;

    @NotBlank(message = "The field lastname cannot be blank or null.")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters")
    private String lastName;

    @NotBlank(message = "The field phone cannot be blank or null.")
    @Pattern(
            regexp = "^\\+?[0-9]{9,15}$",
            message = "Phone must be a valid number (9-15 digits)"
    )
    private String phone;

}
