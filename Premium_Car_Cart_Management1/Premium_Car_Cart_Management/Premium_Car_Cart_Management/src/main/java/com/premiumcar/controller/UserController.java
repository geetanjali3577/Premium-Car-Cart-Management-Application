package com.premiumcar.controller;

import com.premiumcar.dto.LoginRequest;
import com.premiumcar.dto.UserRequestDTO;
import com.premiumcar.dto.UserResponseDTO;
import com.premiumcar.entity.User;


import com.premiumcar.exception.ApiResponse;
import com.premiumcar.repository.UserRepository;
import com.premiumcar.security.JwtUtil;
import com.premiumcar.service.userService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@EnableMethodSecurity
@RequestMapping("/api/users")

public class UserController {

    private final userService userService;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserController(userService userService, UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    // Register User
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<UserResponseDTO>> registerUser(
            @Valid @RequestBody UserRequestDTO request) {

        UserResponseDTO savedUser = userService.registerUser(request);

        ApiResponse<UserResponseDTO> response =
                new ApiResponse<>("Data saved successfully", savedUser);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // Get User by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Get User by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserResponseDTO> getUserByEmail(
            @PathVariable String email) {
        return ResponseEntity.ok(userService.getUserByEmail(email));
    }

    // Get All Users
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers()
    {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    // UPDATE USER (FIXES PUT 405 ERROR)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @PathVariable Long id,
            @Valid  @RequestBody  UserRequestDTO request) {

        UserResponseDTO response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    // Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("userId", user.getId().toString());
        response.put("userRole", user.getRole().name());

        return ResponseEntity.ok(response);
    }
}
