package com.devteria.profile.dto.response;

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
public class ProfileResponse {
    String profileId;
    String userId;
    String email;
    String username;
    String firstName;
    String lastName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    LocalDate dob;
}
