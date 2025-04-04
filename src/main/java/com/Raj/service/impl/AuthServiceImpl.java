package com.Raj.service.impl;

import com.Raj.config.JwtProvider;
import com.Raj.domain.USER_ROLE;
import com.Raj.model.Cart;
import com.Raj.model.User;
import com.Raj.model.VerificationCode;
import com.Raj.repository.UserRepository;
import com.Raj.repository.VerificationCodeRepository;
import com.Raj.response.CartRepository;
import com.Raj.response.SignupRequest;
import com.Raj.service.AuthService;
import com.Raj.service.EmailService;
import com.Raj.utils.OtpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;
   private final CartRepository cartRepository;
   private final JwtProvider jwtProvider;
   private final VerificationCodeRepository verificationCodeRepository;
   private final EmailService emailService;

    @Override
    public void sentLoginOtp(String email) throws Exception {
        String SIGNING_PREFIX="signin";

        if(email.startsWith(SIGNING_PREFIX)) {
            email=email.substring(SIGNING_PREFIX.length());

            User user = userRepository.findByEmail(email);
            if(user==null){
                throw new Exception("User not found with email");
            }

         }
        VerificationCode isExist=verificationCodeRepository.findByEmail(email);
        if (isExist!=null){
            verificationCodeRepository.delete(isExist);
        }

            String otp= OtpUtil.generateOtp();
            VerificationCode verificationCode=new VerificationCode();
            verificationCode.setOtp(otp);
            verificationCode.setEmail(email);
            verificationCodeRepository.save(verificationCode);

            String subject="raj bazaar login/signup otp";
            String text ="your login/signup otp is -"+otp;

            emailService.sendVerificationOtpEmail(email,otp,subject,text);


}

    @Override
    public String createUser(SignupRequest req) throws Exception {

        VerificationCode verificationCode=verificationCodeRepository.findByEmail(req.getEmail());
        if(verificationCode==null || !verificationCode.getOtp().equals(req.getOtp())){
            throw new Exception("Invalid verification code");
        }

        User user = userRepository.findByEmail(req.getEmail());

       if(user==null){
           User createUser=new User();
           createUser.setEmail(req.getEmail());
           createUser.setFullName(req.getFullName());
           createUser.setRole(USER_ROLE.ROLE_CUSTOMER);
           createUser.setMobile("843398948287");
           createUser.setPassword(passwordEncoder.encode(req.getOtp()));

           user=userRepository.save(createUser);

           Cart cart=new Cart();
           cart.setUser(user);
           cartRepository.save(cart);
       }

        List<GrantedAuthority> authorities=new ArrayList<>();
       authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(),null,authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return jwtProvider.generateToken(authentication);
    }
}
