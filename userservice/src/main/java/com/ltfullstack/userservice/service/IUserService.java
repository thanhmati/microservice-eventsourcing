package com.ltfullstack.userservice.service;

import com.ltfullstack.userservice.dto.CreateUserRequestDTO;
import com.ltfullstack.userservice.dto.LoginRequestDto;
import com.ltfullstack.userservice.dto.UserResponseDTO;
import com.ltfullstack.userservice.dto.identity.TokenExchangeResponse;

import java.util.List;

public interface IUserService {
    UserResponseDTO createUser(CreateUserRequestDTO dto);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserById(Long id);
    UserResponseDTO updateUser(Long id, CreateUserRequestDTO dto);
    void deleteUser(Long id);

    TokenExchangeResponse login(LoginRequestDto dto);
}
