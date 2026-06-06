package com.example.StoreMSI.Service;

import com.example.StoreMSI.DTO.Auth.AuthResponse;
import com.example.StoreMSI.DTO.Auth.LoginRequest;
import com.example.StoreMSI.DTO.Auth.RegisterRequest;
import com.example.StoreMSI.Model.User;
import com.example.StoreMSI.Repository.UserRepository;
import com.example.StoreMSI.Security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private UserRepository repo;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    // ================= REGISTER =================
    public String register(RegisterRequest req) {

        // check username
        if (repo.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // check email (optional but recommended)
        if (req.getEmail() != null && repo.findByEmail(req.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(req.getUsername());
        user.setPasswordHash(passwordEncoder.encode(req.getPassword()));
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setRole(User.Role.cashier);

        repo.save(user);

        return "Register success";
    }

    // ================= LOGIN =================
    public AuthResponse login(LoginRequest req) {

        User user = repo.findByEmail(req.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(req.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Wrong password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new AuthResponse(token);
    }
}