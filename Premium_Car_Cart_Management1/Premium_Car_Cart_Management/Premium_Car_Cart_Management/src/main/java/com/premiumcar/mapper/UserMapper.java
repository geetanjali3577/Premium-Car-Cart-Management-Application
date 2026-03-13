package com.premiumcar.mapper;

import com.premiumcar.dto.UserRequestDTO;
import com.premiumcar.dto.UserResponseDTO;
import com.premiumcar.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public User toEntity(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        //  ENCRYPT PASSWORD
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setMobileNo(dto.getMobileNo());
        user.setRole(dto.getRole());
        return user;
    }

    public UserResponseDTO toResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setMobileNo(user.getMobileNo());

        dto.setRole(user.getRole());
        return dto;
    }
}
