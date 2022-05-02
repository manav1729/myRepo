package com.finance.creditcardapp.model;

public enum CreditCardStatus {

    SUCCESS("SUCCESS"),
    CREATED("CREATION_SUCCESS"),
    FAILED("CREATION_FAILED"),
    INVALID_INPUT("INPUT_VALIDATION_FAILED"),
    VALID_INPUT("INPUT_VALIDATION_PASSED");

    public String creditCardStatus;

    CreditCardStatus(String status) {
        this.creditCardStatus = status;
    }

    public String getCreditCardStatus() {
        return creditCardStatus;
    }
}
