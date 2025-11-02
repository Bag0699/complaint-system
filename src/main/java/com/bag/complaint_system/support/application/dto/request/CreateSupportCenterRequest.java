package com.bag.complaint_system.support.application.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateSupportCenterRequest {

  @NotBlank(message = "The field Name cannot be blank or null.")
  @Size(min = 3, max = 200, message = "Name must be between 3 and 200 characters")
  private String name;

  @NotBlank(message = "Street address is required")
  @Size(max = 300, message = "Street address cannot exceed 300 characters")
  private String street;

  @NotNull(message = "The field District cannot be null.")
  private String district;

  @NotBlank(message = "The field Phone cannot be blank or null.")
  @Size(min = 9, max = 15, message = "Phone must be between 9 and 15 characters")
  private String phone;

  @NotBlank(message = "The field Email cannot be blank or null.")
  @Email(message = "Email must be valid")
  @Size(max = 150, message = "Email cannot exceed 150 characters")
  private String email;

  @NotBlank(message = "The field Schedule cannot be blank or null.")
  @Size(max = 200, message = "Schedule cannot exceed 200 characters")
  private String schedule;
}
