package com.Raj.service.impl;

import com.Raj.model.SellerReport;
import com.Raj.service.SellerReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class SellerReportServiceImpl implements SellerReportService {
    @Override
    public SellerReport getSellerReport(String sellerId) {
        return null;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return null;
    }
}
