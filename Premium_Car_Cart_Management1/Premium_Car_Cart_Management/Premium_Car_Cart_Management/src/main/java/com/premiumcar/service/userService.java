package com.premiumcar.service;

import com.premiumcar.dto.UserRequestDTO;
import com.premiumcar.dto.UserResponseDTO;
import com.premiumcar.entity.User;

import java.util.List;

public interface userService {
    UserResponseDTO registerUser(UserRequestDTO request);

    UserResponseDTO getUserById(Long id);

    UserResponseDTO getUserByEmail(String email);

    List<UserResponseDTO> getAllUsers();

    UserResponseDTO updateUser(Long id, UserRequestDTO request);

    void deleteUser(Long id);
}
