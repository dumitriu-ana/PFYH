package com.fyh.tranzactieservice.service;


import com.fyh.tranzactieservice.dto.TranzactieDto;

import java.util.List;

public interface TranzactieService {
    TranzactieDto createTranzactie(TranzactieDto tranzactieDto);
    TranzactieDto getTranzactieById(Long id);
    List<TranzactieDto> getAllTranzactii();
    TranzactieDto updateTranzactie(Long id, TranzactieDto tranzactieDto);
    void deleteTranzactie(Long id);
}