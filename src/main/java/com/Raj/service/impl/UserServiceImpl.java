package com.Raj.service.impl;

import com.Raj.config.JwtProvider;
import com.Raj.model.User;
import com.Raj.repository.UserRepository;
import com.Raj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;


    @Override
    public User findUserByJwtToken(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwt(jwt) ;


        return this.findUserByEmail(email);
    }


    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);
        if(user==null) {
            throw new Exception("user not found - " +email);
        }
        return user;
    }





}
