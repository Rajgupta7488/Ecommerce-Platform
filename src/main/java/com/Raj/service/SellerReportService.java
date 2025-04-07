package com.Raj.service;

import com.Raj.model.Seller;
import com.Raj.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(Seller seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
