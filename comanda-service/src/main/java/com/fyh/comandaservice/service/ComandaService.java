package com.fyh.comandaservice.service;


import com.fyh.comandaservice.dto.ComandaDto;
import com.fyh.comandaservice.dto.ServiciuNumarDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ComandaService {
    ComandaDto createComanda(ComandaDto comandaDto);
    ComandaDto getComandaById(Long id);
    List<ComandaDto> getAllComenzi();
    ComandaDto updateComanda(Long id, ComandaDto comandaDto);
    void deleteComanda(Long id);

    List<ComandaDto> getComenziByClientId(Long clientId);
    List<ComandaDto> getComenziBySpecialistId(Long specialistId);
    ComandaDto raspundeLaComanda(Long id, String mesajSpecialist, MultipartFile fisier) throws IOException;

    List<ServiciuNumarDto> getStatisticiServiciiNumarComenzi();

}