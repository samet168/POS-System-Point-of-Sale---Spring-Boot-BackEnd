package com.example.StoreMSI.Controller;

import com.example.StoreMSI.DTO.User.UserRequest;
import com.example.StoreMSI.DTO.User.UserResponse;
import com.example.StoreMSI.Service.UserService;
import com.example.StoreMSI.Util.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService service;

    // ================= CREATE =================
    @PostMapping
    public ApiResponse<UserResponse> create(
            @Valid @RequestBody UserRequest req
    ) {
        return ApiResponse.ok(service.create(req));
    }

    // ================= UPDATE =================
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> update(
            @PathVariable Long id,
            @RequestBody UserRequest req
    ) {
        return ApiResponse.ok(service.update(id, req));
    }

    // ================= DELETE =================
    @DeleteMapping("/{id}")
    public ApiResponse<String> delete(@PathVariable Long id) {
        service.delete(id);
        return ApiResponse.ok("Deleted successfully");
    }

    // ================= GET ALL =================
    @GetMapping
    public ApiResponse<List<UserResponse>> getAll() {
        return ApiResponse.ok(service.getAll());
    }

    // ================= GET BY ID =================
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getById(@PathVariable Long id) {
        return ApiResponse.ok(service.getById(id));
    }

    // ================= FILTER + PAGINATION =================
    @GetMapping("/filter")
    public ApiResponse<Page<UserResponse>> filter(
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Boolean status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiResponse.ok(
                service.filter(username, email, role, status, page, size)
        );
    }
}