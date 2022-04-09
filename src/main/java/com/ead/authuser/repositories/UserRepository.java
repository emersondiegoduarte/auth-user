package com.ead.authuser.repositories;

import com.ead.authuser.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUserName(String username);

    Optional<UserModel> findByEmail(String email);

    boolean existsByUserName(String userName);
}
