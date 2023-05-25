package org.example.model;

public enum MerchantStatus {
    ACTIV(1), INACTIVE(0);

    private final int status;
    MerchantStatus(int status) {
        this.status = status;
    }
}
