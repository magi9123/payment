package org.example.model;

public enum MerchantStatus {
    ACTIV(0), INACTIVE(1);

    private final int statusNumber;
    MerchantStatus(int statusNumber) {
        this.statusNumber = statusNumber;
    }
}
