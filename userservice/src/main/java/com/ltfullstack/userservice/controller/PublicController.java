package com.ltfullstack.userservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ltfullstack.userservice.dto.CreateUserRequestDTO;
import com.ltfullstack.userservice.dto.UserResponseDTO;
import com.ltfullstack.userservice.service.IUserService;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
    
    @Autowired
    private IUserService userService;


    @PostMapping("register")
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody CreateUserRequestDTO dto) {
        return ResponseEntity.ok(userService.createUser(dto));
    }
}
