package com.Raj.service;

import com.Raj.request.LoginRequest;
import com.Raj.response.AuthResponse;
import com.Raj.response.SignupRequest;

public interface AuthService {


    void sentLoginOtp(String email) throws Exception;
    String createUser(SignupRequest req) throws Exception;
    AuthResponse signing(LoginRequest req);
}
