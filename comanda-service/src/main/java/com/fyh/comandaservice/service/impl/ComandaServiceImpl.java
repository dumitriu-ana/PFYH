package com.fyh.comandaservice.service.impl;


import com.fyh.comandaservice.dto.ComandaDto;
import com.fyh.comandaservice.dto.ServiciuDto;
import com.fyh.comandaservice.dto.SpecialistDto;
import com.fyh.comandaservice.dto.UtilizatorDto;
import com.fyh.comandaservice.entity.Comanda;
import com.fyh.comandaservice.mapper.ComandaMapper;
import com.fyh.comandaservice.repository.ComandaRepository;
import com.fyh.comandaservice.service.ComandaService;
import com.fyh.comandaservice.service.ServiciuClient;
import com.fyh.comandaservice.service.SpecialistClient;
import com.fyh.comandaservice.service.UtilizatorClient;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaServiceImpl implements ComandaService {

    private final ComandaRepository comandaRepository;

    private final UtilizatorClient utilizatorClient;
    private final SpecialistClient specialistClient;
    private final ServiciuClient serviciuClient;
    public ComandaServiceImpl( ComandaRepository comandaRepository, UtilizatorClient utilizatorClient, SpecialistClient specialistClient, ServiciuClient serviciuClient) {
        this.comandaRepository = comandaRepository;
        this.utilizatorClient = utilizatorClient;
        this.specialistClient = specialistClient;
        this.serviciuClient = serviciuClient;
    }

    public ComandaDto getComandaWithDetails(Long idComanda) {
        Comanda comanda = comandaRepository.findById(idComanda).orElse(null);
        if (comanda != null) {
            UtilizatorDto client = utilizatorClient.getUtilizatoriById(comanda.getIdClient());
            SpecialistDto specialist = specialistClient.getSpecialistByUtilizatorId(comanda.getIdSpecialist()); // Sau getSpecialistById
            ServiciuDto serviciu = serviciuClient.getServiciuById(comanda.getIdServiciu());

            ComandaDto comandaDto = ComandaMapper.mapToComandaDto(comanda);
            comandaDto.setIdClient(client.getId());
            comandaDto.setIdSpecialist(specialist.getId());
            comandaDto.setId(serviciu.getId());

            return comandaDto;
        }
        return null;
    }

    @Override
    public ComandaDto createComanda(ComandaDto comandaDto) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        comandaDto.setDataCreare(now);
        Timestamp dataMaximaLivrare = new Timestamp(now.getTime() + 5L * 24 * 60 * 60 * 1000);
        comandaDto.setDataMaximaLivrare(dataMaximaLivrare);
        if (comandaDto.getStatus() == null) {
            comandaDto.setStatus("Plasata");
        }
        Comanda comanda = ComandaMapper.mapToComanda(comandaDto);
        Comanda saved = comandaRepository.save(comanda);

        return ComandaMapper.mapToComandaDto(saved);
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