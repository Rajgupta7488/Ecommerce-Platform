package com.Raj.service;

import com.Raj.response.SignupRequest;

public interface AuthService {


    void sentLoginOtp(String email) throws Exception;
    String createUser(SignupRequest req) throws Exception;
}
