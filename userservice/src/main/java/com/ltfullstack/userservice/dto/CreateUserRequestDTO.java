package com.ltfullstack.userservice.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateUserRequestDTO {
    private String email;
    private String username;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String name;
}