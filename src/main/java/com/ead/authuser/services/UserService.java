package com.ead.authuser.services;

import com.ead.authuser.model.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    List<UserModel> findAll();

    Optional<UserModel> getUserById(UUID userId);

    void delete(UserModel userModel);

    Optional<UserModel> findByUserName(String username);

    Optional<UserModel> findByEmail(String email);

    boolean existsByUserName(String userName);

    UserModel save(UserModel userModel);

    UserModel updateUser(UserModel userModel);
}
