package com.Raj.controller;


import com.Raj.model.User;
import com.Raj.domain.USER_ROLE;
import com.Raj.response.APIResponse;

import com.Raj.model.VerificationCode;
import com.Raj.repository.UserRepository;
import com.Raj.response.AuthResponse;
import com.Raj.response.SignupRequest;
import com.Raj.service.AuthService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor

public class AuthController {

    private final UserRepository userRepository;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignupRequest req) throws Exception {

        String jwt = authService.createUser(req);

        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register successfully");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/sent/login/signup-otp")
    public ResponseEntity<APIResponse> sentOtpHandler(@RequestBody VerificationCode req) throws Exception {

        authService.sentLoginOtp(req.getEmail());

        APIResponse res = new APIResponse();
        res.setMessage("otp sent  successfully");


        return ResponseEntity.ok(res);
    }
}
