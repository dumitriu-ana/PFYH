package com.fyh.tranzactieservice.dto;

import java.math.BigDecimal;

public class CreatePaymentIntentRequest {
    private BigDecimal suma;

    public BigDecimal getSuma() {
        return suma;
    }
    public void setSuma(BigDecimal suma) {
        this.suma = suma;
    }
}