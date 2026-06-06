package com.example.StoreMSI.DTO.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequest {

    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
    private Boolean status;
}