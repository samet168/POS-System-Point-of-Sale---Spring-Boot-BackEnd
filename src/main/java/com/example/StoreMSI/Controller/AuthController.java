package com.example.StoreMSI.Controller;

import com.example.StoreMSI.DTO.Auth.*;
import com.example.StoreMSI.Service.AuthService;
import com.example.StoreMSI.Service.TokenBlacklistService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthService service;
    @Autowired private TokenBlacklistService blacklistService;

    // REGISTER
    @PostMapping("/register")
    public ApiResponse<String> register(@RequestBody RegisterRequest req) {
        return ApiResponse.ok(service.register(req));
    }

    // LOGIN
    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@RequestBody LoginRequest req) {
        return ApiResponse.ok(service.login(req));
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(
            @RequestHeader("Authorization") String token
    ) {
        service.logout(token);
        return ApiResponse.ok("Logout success");
    }

}