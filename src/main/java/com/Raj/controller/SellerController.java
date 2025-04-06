package com.Raj.controller;

import com.Raj.config.JwtProvider;
import com.Raj.domain.AccountStatus;
import com.Raj.exceptions.SellerExceptions;
import com.Raj.model.Seller;
import com.Raj.model.SellerReport;
import com.Raj.model.VerificationCode;
import com.Raj.repository.VerificationCodeRepository;
import com.Raj.request.LoginRequest;
import com.Raj.response.AuthResponse;
import com.Raj.service.AuthService;
import com.Raj.service.EmailService;
import com.Raj.service.SellerService;
import com.Raj.utils.OtpUtil;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seller")
public class SellerController {
    private final SellerService sellerService;
    private final AuthService authService;
    private final VerificationCodeRepository verificationCodeRepository;
    private final EmailService emailService;
    private final JwtProvider jwtProvider;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller(
            @RequestBody LoginRequest req
    ) throws Exception{

        String otp=req.getOtp();
        String email =req.getEmail();

        System.out.println(otp+" "+email);


        req.setEmail("seller_"+email);
        System.out.println(otp+" "+email);
        AuthResponse authResponse =authService.signing(req);

        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<Seller> verifySellerEmail(
            @PathVariable String otp) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("wrong otp..");
        }

        Seller seller = sellerService.verifyEmail(verificationCode.getEmail(),otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);


    }

    @PostMapping
    public ResponseEntity<Seller> createSeller(
            @RequestBody Seller seller) throws Exception, MessagingException {
        Seller seller1 = sellerService.createSeller(seller);

        String otp = OtpUtil.generateOtp();
        VerificationCode verificationCode=new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());
        verificationCodeRepository.save(verificationCode);

        String subject = "Raj Bazaar Email Verification Code";
        String text = "Welcome Bazaar Email Verification Code" ;
        String frontendUrl="http://localhost:3000/seller/verify/"+otp;
        emailService.sendVerificationOtpEmail(seller.getEmail(),verificationCode.getOtp(),subject,text + frontendUrl);
        return new ResponseEntity<>(seller1, HttpStatus.CREATED);
    }
    @GetMapping("{/id}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Long id) throws SellerExceptions {
        Seller seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }
    @GetMapping("/profile")
    public ResponseEntity<Seller> getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwt(jwt);
        Seller seller = sellerService.getSellerProfile(jwt);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

//    @GetMapping("/report")
//    public ResponseEntity<List<Seller>> getSellerReport(
//            @RequestHeader("Authorization") String jwt)
//            throws Exception {
//        String email = jwtProvider.getEmailFromJwt(jwt);
//        Seller seller = sellerService.getSellerByEmail(email);
//        SellerReport report = sellerReportService.getSellerReport(seller);
//    }

    @GetMapping
    public ResponseEntity<List<Seller>> getAllSellers(
            @RequestParam(required = false)AccountStatus status) {
        List<Seller> sellers = sellerService.getAllSellers(status);
        return  ResponseEntity.ok(sellers);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Seller> updateSeller
            (@RequestHeader("Authorization") String jwt,
             @RequestBody Seller seller) throws Exception {

        Seller profile = sellerService.getSellerProfile(jwt);
        Seller updatedSeller = sellerService.updateSeller(profile.getId(),seller);
        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller
            (@PathVariable Long id) throws Exception {
        sellerService.deleteSeller(id);
        return ResponseEntity.noContent().build();
    }
}

