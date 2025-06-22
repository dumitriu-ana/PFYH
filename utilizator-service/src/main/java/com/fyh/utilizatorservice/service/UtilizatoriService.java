package com.fyh.utilizatorservice.service;



import com.fyh.utilizatorservice.dto.UtilizatorDto;

import java.math.BigDecimal;
import java.util.List;

public interface UtilizatoriService {
    UtilizatorDto createUtilizatori(UtilizatorDto utilizatorDto);
    UtilizatorDto getUtilizatoriById(Long id);
    List<UtilizatorDto> getAllUtilizatori();
    UtilizatorDto updateUtilizatori(Long id, UtilizatorDto utilizatorDto);
    void deleteUtilizatori(Long id);
    void changeTip(Long id, String noulTip);

    void adaugaLaSold(Long utilizatorId, BigDecimal sumaDeAdaugat);
}
