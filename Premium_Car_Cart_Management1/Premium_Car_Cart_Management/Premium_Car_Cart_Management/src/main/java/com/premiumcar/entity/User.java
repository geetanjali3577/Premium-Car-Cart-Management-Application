package com.premiumcar.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = " Name is mandatory")
    private String  name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is mandatory")
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    @Column(name = "email", nullable = false, length = 250, unique = true)
    private String email;

    @NotBlank(message = "Password is mandatory")
    @NotBlank(message = "Password cannot be blank")
    @Column(name = "password", nullable = false, length = 250)
    private String password;


    @NotBlank(message = "Mobile number Cannot be blank")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be of 10 digits")
    @Column(name = "mobile_no", unique = true)
    private String mobileNo;


    @NotNull(message = "User role is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 200)
    private UserRole role;
}
