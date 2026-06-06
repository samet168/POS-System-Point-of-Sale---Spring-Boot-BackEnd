package com.example.StoreMSI.Mapper;

import com.example.StoreMSI.DTO.User.UserRequest;
import com.example.StoreMSI.DTO.User.UserResponse;
import com.example.StoreMSI.Model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserRequest req) {
        if (req == null) return null;

        User u = new User();
        u.setUsername(req.getUsername());
        u.setPasswordHash(req.getPassword());
        u.setEmail(req.getEmail());
        u.setPhone(req.getPhone());

        if (req.getRole() != null) {
            u.setRole(User.Role.valueOf(req.getRole()));
        }

        if (req.getStatus() != null) {
            u.setStatus(req.getStatus());
        }

        return u;
    }

    public UserResponse toResponse(User u) {
        if (u == null) return null;

        UserResponse res = new UserResponse();
        res.setId(u.getId());
        res.setUsername(u.getUsername());
        res.setEmail(u.getEmail());
        res.setPhone(u.getPhone());
        res.setRole(u.getRole().name());
        res.setStatus(u.getStatus());
        res.setCreatedAt(u.getCreatedAt());

        return res;
    }
}