package com.ead.authuser.controllers;

import com.ead.authuser.dto.UserDTO;
import com.ead.authuser.model.UserModel;
import com.ead.authuser.services.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userService.getUserById(userId);
        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "userId") UUID userId){
        Optional<UserModel> userModelOptional = userService.getUserById(userId);
        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

       userService.delete(userModelOptional.get());
       return ResponseEntity.status(HttpStatus.OK).body("User deleted");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") UUID userId,
                                             @JsonView(UserDTO.UserView.UserPut.class) @RequestBody UserDTO userDTO){
        Optional<UserModel> userModelOptional = userService.getUserById(userId);
        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        UserModel userModel = userModelOptional.get();
        userModel.setCpf(userDTO.getCpf());
        userModel.setFullName(userDTO.getFullName());
        userModel.setPhoneNumber(userDTO.getPhoneNumber());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updateUser(userModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable(value = "userId") UUID userId,
                                             @JsonView(UserDTO.UserView.PasswordPut.class) @RequestBody UserDTO userDTO){
        Optional<UserModel> userModelOptional = userService.getUserById(userId);
        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserModel userModel = userModelOptional.get();
        if(!userDTO.getOldPassword().equals(userModel.getPassword())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Old Password does not match!");
        }
        userModel.setPassword(userDTO.getPassword());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updateUser(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable(value = "userId") UUID userId,
                                                 @JsonView(UserDTO.UserView.ImagePut.class) @RequestBody UserDTO userDTO){
        Optional<UserModel> userModelOptional = userService.getUserById(userId);
        if(userModelOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        UserModel userModel = userModelOptional.get();
        userModel.setImageUrl(userDTO.getImageUrl());
        userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        userService.updateUser(userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userModel);
    }


}
