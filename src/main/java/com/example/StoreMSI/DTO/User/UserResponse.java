package com.example.StoreMSI.DTO.User;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;

    private String email;
    private String phone;

    private String role;
    private Boolean status;

    private LocalDateTime createdAt;
}