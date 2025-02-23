package com.devteria.profile.dto.request;

import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegistrationRequest {
    @Size(min = 4, message = "INVALID_USERNAME")
    String username;

    @Size(min = 6, message = "INVALID_PASSWORD")
    String password;

    String email;
    String firstName;
    String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob;
}
