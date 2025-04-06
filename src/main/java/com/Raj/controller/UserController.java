package com.Raj.controller;

import com.Raj.domain.USER_ROLE;
import com.Raj.model.User;
import com.Raj.response.AuthResponse;
import com.Raj.response.SignupRequest;
import com.Raj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;


    @GetMapping("/users/profile")
    public ResponseEntity<User> createUserHandler(@RequestHeader("Authorization") String jwt) throws Exception {

        User user = userService.findUserByJwtToken(jwt);



        return ResponseEntity.ok(user);
    }


}
