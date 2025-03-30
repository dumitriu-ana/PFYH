package com.fyh.specialistserviciuservice.service.impl;


import com.fyh.specialistserviciuservice.dto.SpecialistiServiciiDto;
import com.fyh.specialistserviciuservice.entity.SpecialistiServicii;
import com.fyh.specialistserviciuservice.mapper.SpecialistiServiciiMapper;
import com.fyh.specialistserviciuservice.repository.SpecialistiServiciiRepository;
import com.fyh.specialistserviciuservice.service.SpecialistiServiciiService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistiServiciiServiceImpl implements SpecialistiServiciiService {

    private final SpecialistiServiciiRepository specialistiServiciiRepository;

    public SpecialistiServiciiServiceImpl(SpecialistiServiciiRepository specialistiServiciiRepository) {
        this.specialistiServiciiRepository = specialistiServiciiRepository;
    }

    @Override
    public SpecialistiServiciiDto createSpecialistiServicii(SpecialistiServiciiDto specialistiServiciiDto) {
        SpecialistiServicii specialistiServicii = SpecialistiServiciiMapper.mapToSpecialistiServicii(specialistiServiciiDto);
        SpecialistiServicii savedSpecialistiServicii = specialistiServiciiRepository.save(specialistiServicii);
        return SpecialistiServiciiMapper.mapToSpecialistiServiciiDto(savedSpecialistiServicii);
    }

    @Override
    public SpecialistiServiciiDto getSpecialistiServiciiById(Long id) {
        SpecialistiServicii specialistiServicii = specialistiServiciiRepository.findById(id).orElse(null);
        return SpecialistiServiciiMapper.mapToSpecialistiServiciiDto(specialistiServicii);
    }

    @Override
    public List<SpecialistiServiciiDto> getAllSpecialistiServicii() {
        List<SpecialistiServicii> specialistiServiciiList = specialistiServiciiRepository.findAll();
        return specialistiServiciiList.stream()
                .map(SpecialistiServiciiMapper::mapToSpecialistiServiciiDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialistiServiciiDto updateSpecialistiServicii(Long id, SpecialistiServiciiDto specialistiServiciiDto) {
        SpecialistiServicii existingSpecialistiServicii = specialistiServiciiRepository.findById(id).orElse(null);
        if (existingSpecialistiServicii != null) {
            specialistiServiciiDto.setId(id);
            SpecialistiServicii updatedSpecialistiServicii = specialistiServiciiRepository.save(SpecialistiServiciiMapper.mapToSpecialistiServicii(specialistiServiciiDto));
            return SpecialistiServiciiMapper.mapToSpecialistiServiciiDto(updatedSpecialistiServicii);
        }
        return null;
    }

    @Override
    public void deleteSpecialistiServicii(Long id) {
        specialistiServiciiRepository.deleteById(id);
    }
}