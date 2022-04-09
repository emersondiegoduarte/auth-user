package com.ead.authuser.controllers;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.enums.UserStatus;
import com.ead.authuser.enums.UserType;
import com.ead.authuser.model.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> createUser(@RequestBody
                                                 @JsonView(UserDTO.UserView.RegistrationPost.class) UserDTO userDTO){
        var userExists = userService.existsByUserName(userDTO.getUsername());
        if(userExists){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        var user = userService.findByEmail(userDTO.getEmail());
        if(user.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        }

        UserModel userModel = UserModel.builder()
                .cpf(userDTO.getCpf())
                .email(userDTO.getEmail())
                .fullName(userDTO.getFullName())
                .userName(userDTO.getUsername())
                .userType(UserType.STUDENT)
                .password(userDTO.getPassword())
                .phoneNumber(userDTO.getPhoneNumber())
                .userStatus(UserStatus.ACTIVE)
                .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
                .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userModel));
    }
}
