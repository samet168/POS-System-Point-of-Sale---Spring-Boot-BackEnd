package com.example.StoreMSI.Repository;

import com.example.StoreMSI.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);


}