package com.fyh.comandaservice.service;


import com.fyh.comandaservice.dto.ComandaDto;

import java.util.List;

public interface ComandaService {
    ComandaDto createComanda(ComandaDto comandaDto);
    ComandaDto getComandaById(Long id);
    List<ComandaDto> getAllComenzi();
    ComandaDto updateComanda(Long id, ComandaDto comandaDto);
    void deleteComanda(Long id);
}