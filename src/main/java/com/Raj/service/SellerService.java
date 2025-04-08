package com.Raj.service;

import com.Raj.domain.AccountStatus;
import com.Raj.exceptions.SellerExceptions;
import com.Raj.model.Seller;

import java.util.List;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws SellerExceptions;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus status);
    Seller updateSeller(Long is,Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email,String otp) throws Exception;
    Seller updateAccountStatus(Long sellerId,AccountStatus status) throws Exception;



}
