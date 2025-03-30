package com.fyh.specializareservice.service.impl;

import com.fyh.specializareservice.dto.SpecializareDto;
import com.fyh.specializareservice.entity.Specializare;
import com.fyh.specializareservice.mapper.SpecializareMapper;
import com.fyh.specializareservice.repository.SpecializareRepository;
import com.fyh.specializareservice.service.SpecializareService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecializareServiceImpl implements SpecializareService {

    private final SpecializareRepository specializareRepository;

    public SpecializareServiceImpl(SpecializareRepository specializareRepository) {
        this.specializareRepository = specializareRepository;
    }

    @Override
    public SpecializareDto createSpecializare(SpecializareDto specializareDto) {
        Specializare specializare = SpecializareMapper.mapToSpecializare(specializareDto);
        Specializare savedSpecializare = specializareRepository.save(specializare);
        return SpecializareMapper.mapToSpecializareDto(savedSpecializare);
    }

    @Override
    public SpecializareDto getSpecializareById(Long id) {
        Specializare specializare = specializareRepository.findById(id).orElse(null);
        return SpecializareMapper.mapToSpecializareDto(specializare);
    }

    @Override
    public List<SpecializareDto> getAllSpecializari() {
        List<Specializare> specializari = specializareRepository.findAll();
        return specializari.stream()
                .map(SpecializareMapper::mapToSpecializareDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecializareDto updateSpecializare(Long id, SpecializareDto specializareDto) {
        Specializare existingSpecializare = specializareRepository.findById(id).orElse(null);
        if (existingSpecializare != null) {
            specializareDto.setId(id);
            Specializare updatedSpecializare = specializareRepository.save(SpecializareMapper.mapToSpecializare(specializareDto));
            return SpecializareMapper.mapToSpecializareDto(updatedSpecializare);
        }
        return null;
    }

    @Override
    public void deleteSpecializare(Long id) {
        specializareRepository.deleteById(id);
    }
}