package com.fyh.serviciuservice.service.impl;


import com.fyh.serviciuservice.dto.ServiciuDto;
import com.fyh.serviciuservice.entity.Serviciu;
import com.fyh.serviciuservice.mapper.ServiciuMapper;
import com.fyh.serviciuservice.repository.ServiciuRepository;
import com.fyh.serviciuservice.service.ServiciuService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServiciuServiceImpl implements ServiciuService {

    private final ServiciuRepository serviciuRepository;

    public ServiciuServiceImpl(@Qualifier("serviciuRepository") ServiciuRepository serviciuRepository) {
        this.serviciuRepository = serviciuRepository;
    }

    @Override
    public ServiciuDto createServiciu(ServiciuDto serviciuDto) {
        Serviciu serviciu = ServiciuMapper.mapToServiciu(serviciuDto);
        Serviciu savedServiciu = serviciuRepository.save(serviciu);
        return ServiciuMapper.mapToServiciuDto(savedServiciu);
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
}