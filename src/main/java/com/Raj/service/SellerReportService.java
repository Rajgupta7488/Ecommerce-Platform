package com.Raj.service;

import com.Raj.model.SellerReport;

public interface SellerReportService {
    SellerReport getSellerReport(String sellerId);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
