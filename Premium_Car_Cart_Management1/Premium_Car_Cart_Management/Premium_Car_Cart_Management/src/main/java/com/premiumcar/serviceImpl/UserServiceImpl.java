package com.premiumcar.serviceImpl;

import com.premiumcar.dto.UserRequestDTO;
import com.premiumcar.dto.UserResponseDTO;
import com.premiumcar.entity.User;
import com.premiumcar.exception.ApiException;
import com.premiumcar.mapper.UserMapper;
import com.premiumcar.repository.UserRepository;

import com.premiumcar.service.userService;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements userService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public UserResponseDTO registerUser(UserRequestDTO request) {

        // Duplicate Email Check
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(HttpStatus.CONFLICT, "Email already registered");
        }

        //  Duplicate Mobile Check (optional but recommended)
        if (userRepository.existsByMobileNo(request.getMobileNo())) {
            throw new ApiException(HttpStatus.CONFLICT, "Mobile number already registered");
        }

        User user = userMapper.toEntity(request);
        User savedUser = userRepository.save(user);

        return userMapper.toResponseDTO(savedUser);

    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found" + id));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public UserResponseDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found" + email));
        return userMapper.toResponseDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserRequestDTO request) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found"));

        //  Email duplication check
        if (!existingUser.getEmail().equals(request.getEmail())
                && userRepository.existsByEmail(request.getEmail())) {

            throw new ApiException(
                    HttpStatus.CONFLICT,
                    "Email already in use"
            );
        }

        //  Mobile duplication check
        if (!existingUser.getMobileNo().equals(request.getMobileNo())
                && userRepository.existsByMobileNo(request.getMobileNo())) {

            throw new ApiException(
                    HttpStatus.CONFLICT,
                    "Mobile number already in use"
            );
        }

        existingUser.setName(request.getName());
        existingUser.setEmail(request.getEmail());
        existingUser.setPassword(request.getPassword());
        existingUser.setMobileNo(request.getMobileNo());
        existingUser.setRole(request.getRole());

        User updatedUser = userRepository.save(existingUser);
        return userMapper.toResponseDTO(updatedUser);
    }


    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new ApiException(HttpStatus.NOT_FOUND, "User not found" + id));
        userRepository.delete(user);
    }
}
