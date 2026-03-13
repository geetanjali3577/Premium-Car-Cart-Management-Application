package com.premiumcar.dto;

import com.premiumcar.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserResponseDTO {
    private Long id;
    private String  name;
    private String email;

    private String mobileNo;
    private UserRole role;

}
