package com.fyh.serviciuservice.service.impl;


import com.fyh.serviciuservice.dto.ServiciuDto;
import com.fyh.serviciuservice.entity.Serviciu;
import com.fyh.serviciuservice.mapper.ServiciuMapper;
import com.fyh.serviciuservice.repository.ServiciuRepository;
import com.fyh.serviciuservice.service.ServiciuService;
import com.fyh.serviciuservice.service.SpecialistClient;
import com.fyh.serviciuservice.service.SpecializareClient;
import com.fyh.serviciuservice.dto.SpecializareDto;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiciuServiceImpl implements ServiciuService {

    private final ServiciuRepository serviciuRepository;
    private final SpecializareClient specializareClient;
    private final SpecialistClient specialistClient;

    public ServiciuServiceImpl(@Qualifier("serviciuRepository") ServiciuRepository serviciuRepository, SpecializareClient specializareClient, SpecialistClient specialistClient) {
        this.serviciuRepository = serviciuRepository;
        this.specializareClient = specializareClient;
        this.specialistClient = specialistClient;
    }

    @Override
    public ServiciuDto createServiciu(ServiciuDto serviciuDto) {
        Serviciu serviciu = ServiciuMapper.mapToServiciu(serviciuDto);
        Serviciu savedServiciu = serviciuRepository.save(serviciu);
        return ServiciuMapper.mapToServiciuDto(savedServiciu);
    }
    public ServiciuDto getServiciuWithSpecializare(Long idServiciu) {
        Serviciu serviciu = serviciuRepository.findById(idServiciu).orElse(null);
        if (serviciu != null) {
            ServiciuDto serviciuDto = ServiciuMapper.mapToServiciuDto(serviciu);
            if (serviciu.getIdSpecializare() != null) {
                SpecializareDto specializare = specializareClient.getSpecializareById(serviciu.getIdSpecializare());
                serviciuDto.setIdSpecializare(specializare.getId());
            }
            return serviciuDto;
        }
        return null;
    }

    @Override
    public ServiciuDto getServiciuById(Long id) {
        Serviciu serviciu = serviciuRepository.findById(id).orElse(null);
        return ServiciuMapper.mapToServiciuDto(serviciu);
    }

    @Override
    public List<ServiciuDto> getAllServicii() {
        List<Serviciu> servicii = serviciuRepository.findAll();
        return servicii.stream()
                .map(ServiciuMapper::mapToServiciuDto)
                .collect(Collectors.toList());
    }

    @Override
    public ServiciuDto updateServiciu(Long id, ServiciuDto serviciuDto) {
        Serviciu existingServiciu = serviciuRepository.findById(id).orElse(null);
        if (existingServiciu != null) {
            serviciuDto.setId(id);
            Serviciu updatedServiciu = serviciuRepository.save(ServiciuMapper.mapToServiciu(serviciuDto));
            return ServiciuMapper.mapToServiciuDto(updatedServiciu);
        }
        return null;
    }

    @Override
    public void deleteServiciu(Long id) {
        serviciuRepository.deleteById(id);
    }


    @Override
    public List<ServiciuDto> findBySpecialist(Long idSpecialist) {
        List<Long> ids;
        try {
            ids = specialistClient.getServiciiIdsForSpecialist(idSpecialist);
        } catch (feign.FeignException.NotFound e) {
            // specialistul nu există sau nu are id-uri: întoarce listă goală
            return Collections.emptyList();
        }

        if (ids.isEmpty()) {
            return Collections.emptyList();
        }
        return serviciuRepository.findAllById(ids)
                .stream()
                .map(ServiciuMapper::mapToServiciuDto)
                .collect(Collectors.toList());
    }

}