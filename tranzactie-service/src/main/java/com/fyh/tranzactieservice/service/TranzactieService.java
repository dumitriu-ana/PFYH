package com.fyh.tranzactieservice.service;


import com.fyh.tranzactieservice.dto.TranzactieDto;
import com.stripe.exception.StripeException;

import java.math.BigDecimal;
import java.util.List;

public interface TranzactieService {
    TranzactieDto createTranzactie(TranzactieDto tranzactieDto);
    TranzactieDto getTranzactieById(Long id);
    List<TranzactieDto> getAllTranzactii();
    TranzactieDto updateTranzactie(Long id, TranzactieDto tranzactieDto);
    void deleteTranzactie(Long id);
    String createPaymentIntent(BigDecimal suma) throws StripeException;

}