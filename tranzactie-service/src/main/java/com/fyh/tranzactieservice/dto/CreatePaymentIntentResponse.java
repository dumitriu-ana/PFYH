package com.fyh.tranzactieservice.dto;

public class CreatePaymentIntentResponse {
    private String clientSecret;

    public CreatePaymentIntentResponse(String clientSecret) {
        this.clientSecret = clientSecret;
    }
    public String getClientSecret() {
        return clientSecret;
    }
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}