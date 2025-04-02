package com.Raj.domain;

public enum AccountStatus {

    PENDING_VERIFICATION,
    ACTIVE,
    SUSPENDED,
    DEACTIVATED,
    BANNED,
    CLOSED;

    public enum PaymentStatus {

        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED
    }
}
