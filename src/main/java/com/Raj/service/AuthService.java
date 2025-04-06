package com.Raj.service;

import com.Raj.domain.USER_ROLE;
import com.Raj.request.LoginRequest;
import com.Raj.response.AuthResponse;
import com.Raj.response.SignupRequest;

public interface AuthService {


    void sentLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req) throws Exception;
}
