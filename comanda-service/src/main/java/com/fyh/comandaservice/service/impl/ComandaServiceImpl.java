package com.fyh.comandaservice.service.impl;


import com.fyh.comandaservice.dto.ComandaDto;
import com.fyh.comandaservice.entity.Comanda;
import com.fyh.comandaservice.mapper.ComandaMapper;
import com.fyh.comandaservice.repository.ComandaRepository;
import com.fyh.comandaservice.service.ComandaService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaServiceImpl implements ComandaService {

    private final ComandaRepository comandaRepository;

    public ComandaServiceImpl(@Qualifier("comandaRepository") ComandaRepository comandaRepository) {
        this.comandaRepository = comandaRepository;
    }

    @Override
    public ComandaDto createComanda(ComandaDto comandaDto) {
        Comanda comanda = ComandaMapper.mapToComanda(comandaDto);
        Comanda savedComanda = comandaRepository.save(comanda);
        return ComandaMapper.mapToComandaDto(savedComanda);
    }

    @Override
    public ComandaDto getComandaById(Long id) {
        Comanda comanda = comandaRepository.findById(id).orElse(null);
        return ComandaMapper.mapToComandaDto(comanda);
    }

    @Override
    public List<ComandaDto> getAllComenzi() {
        List<Comanda> comenzi = comandaRepository.findAll();
        return comenzi.stream()
                .map(ComandaMapper::mapToComandaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComandaDto updateComanda(Long id, ComandaDto comandaDto) {
        Comanda existingComanda = comandaRepository.findById(id).orElse(null);
        if (existingComanda != null) {
            comandaDto.setId(id);
            Comanda updatedComanda = comandaRepository.save(ComandaMapper.mapToComanda(comandaDto));
            return ComandaMapper.mapToComandaDto(updatedComanda);
        }
        return null;
    }

    @Override
    public void deleteComanda(Long id) {
        comandaRepository.deleteById(id);
    }
}