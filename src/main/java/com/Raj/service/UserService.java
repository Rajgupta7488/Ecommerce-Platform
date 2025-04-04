package com.Raj.service;

import com.Raj.model.User;

public interface UserService {

    User findUserByJwtToken(String jwt);
    User findUserByEmail(String email);
}
