package com.fyh.tranzactieservice.service.impl;


import com.fyh.tranzactieservice.dto.TranzactieDto;
import com.fyh.tranzactieservice.entity.Tranzactie;
import com.fyh.tranzactieservice.mapper.TranzactieMapper;
import com.fyh.tranzactieservice.repository.TranzactieRepository;
import com.fyh.tranzactieservice.service.ComandaClient;
import com.fyh.tranzactieservice.service.TranzactieService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TranzactieServiceImpl implements TranzactieService {

    private final TranzactieRepository tranzactieRepository;

    private final ComandaClient comandaClient;
    public TranzactieServiceImpl(TranzactieRepository tranzactieRepository, ComandaClient comandaClient) {
        this.tranzactieRepository = tranzactieRepository;
        this.comandaClient = comandaClient;
    }

    @Override
    public TranzactieDto createTranzactie(TranzactieDto tranzactieDto) {
        Tranzactie tranzactie = TranzactieMapper.mapToTranzactie(tranzactieDto);
        Tranzactie savedTranzactie = tranzactieRepository.save(tranzactie);
        return TranzactieMapper.mapToTranzactieDto(savedTranzactie);
    }

    @Override
    public TranzactieDto getTranzactieById(Long id) {
        Tranzactie tranzactie = tranzactieRepository.findById(id).orElse(null);
        return TranzactieMapper.mapToTranzactieDto(tranzactie);
    }

    @Override
    public List<TranzactieDto> getAllTranzactii() {
        List<Tranzactie> tranzactii = tranzactieRepository.findAll();
        return tranzactii.stream()
                .map(TranzactieMapper::mapToTranzactieDto)
                .collect(Collectors.toList());
    }

    @Override
    public TranzactieDto updateTranzactie(Long id, TranzactieDto tranzactieDto) {
        Tranzactie existingTranzactie = tranzactieRepository.findById(id).orElse(null);
        if (existingTranzactie != null) {
            tranzactieDto.setId(id);
            Tranzactie updatedTranzactie = tranzactieRepository.save(TranzactieMapper.mapToTranzactie(tranzactieDto));
            return TranzactieMapper.mapToTranzactieDto(updatedTranzactie);
        }
        return null;
    }

    @Override
    public void deleteTranzactie(Long id) {
        tranzactieRepository.deleteById(id);
    }
}