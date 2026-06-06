package com.example.StoreMSI.Service;

import com.example.StoreMSI.DTO.User.UserRequest;
import com.example.StoreMSI.DTO.User.UserResponse;
import com.example.StoreMSI.Mapper.UserMapper;
import com.example.StoreMSI.Model.User;
import com.example.StoreMSI.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired private UserRepository repo;
    @Autowired private UserMapper mapper;

    // ================= CREATE =================
    public UserResponse create(UserRequest req) {

        if (repo.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = mapper.toEntity(req);
        return mapper.toResponse(repo.save(user));
    }

    // ================= UPDATE =================
    public UserResponse update(Long id, UserRequest req) {

        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());

        if (req.getPassword() != null) {
            user.setPasswordHash(req.getPassword());
        }

        if (req.getRole() != null) {
            user.setRole(User.Role.valueOf(req.getRole()));
        }

        if (req.getStatus() != null) {
            user.setStatus(req.getStatus());
        }

        return mapper.toResponse(repo.save(user));
    }

    // ================= DELETE =================
    public void delete(Long id) {
        repo.deleteById(id);
    }

    // ================= GET ALL =================
    public List<UserResponse> getAll() {
        return repo.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    // ================= GET BY ID =================
    public UserResponse getById(Long id) {
        return mapper.toResponse(
                repo.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"))
        );
    }

    // ================= FILTER + PAGINATION =================
    public Page<UserResponse> filter(
            String username,
            String email,
            String role,
            Boolean status,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(page, size,
                Sort.by(Sort.Direction.DESC, "id"));

        Specification<User> spec = Specification.unrestricted();

        if (username != null && !username.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("username")),
                            "%" + username.toLowerCase() + "%"));
        }

        if (email != null && !email.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("email")),
                            "%" + email.toLowerCase() + "%"));
        }

        if (role != null && !role.trim().isEmpty()) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("role"),
                            User.Role.valueOf(role)));
        }

        if (status != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("status"), status));
        }

        return repo.findAll(spec, pageable)
                .map(mapper::toResponse);
    }
}