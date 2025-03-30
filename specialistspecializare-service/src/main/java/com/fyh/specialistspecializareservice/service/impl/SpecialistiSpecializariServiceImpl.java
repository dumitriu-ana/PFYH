package com.fyh.specialistspecializareservice.service.impl;

import com.fyh.specialistspecializareservice.dto.SpecialistiSpecializariDto;
import com.fyh.specialistspecializareservice.entity.SpecialistiSpecializari;
import com.fyh.specialistspecializareservice.mapper.SpecialistiSpecializariMapper;
import com.fyh.specialistspecializareservice.repository.SpecialistiSpecializariRepository;
import com.fyh.specialistspecializareservice.service.SpecialistiSpecializariService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistiSpecializariServiceImpl implements SpecialistiSpecializariService {

    private final SpecialistiSpecializariRepository specialistiSpecializariRepository;

    public SpecialistiSpecializariServiceImpl(SpecialistiSpecializariRepository specialistiSpecializariRepository) {
        this.specialistiSpecializariRepository = specialistiSpecializariRepository;
    }

    @Override
    public SpecialistiSpecializariDto createSpecialistiSpecializari(SpecialistiSpecializariDto specialistiSpecializariDto) {
        SpecialistiSpecializari specialistiSpecializari = SpecialistiSpecializariMapper.mapToSpecialistiSpecializari(specialistiSpecializariDto);
        SpecialistiSpecializari savedSpecialistiSpecializari = specialistiSpecializariRepository.save(specialistiSpecializari);
        return SpecialistiSpecializariMapper.mapToSpecialistiSpecializariDto(savedSpecialistiSpecializari);
    }

    @Override
    public SpecialistiSpecializariDto getSpecialistiSpecializariById(Long id) {
        SpecialistiSpecializari specialistiSpecializari = specialistiSpecializariRepository.findById(id).orElse(null);
        return SpecialistiSpecializariMapper.mapToSpecialistiSpecializariDto(specialistiSpecializari);
    }

    @Override
    public List<SpecialistiSpecializariDto> getAllSpecialistiSpecializari() {
        List<SpecialistiSpecializari> specialistiSpecializariList = specialistiSpecializariRepository.findAll();
        return specialistiSpecializariList.stream()
                .map(SpecialistiSpecializariMapper::mapToSpecialistiSpecializariDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialistiSpecializariDto updateSpecialistiSpecializari(Long id, SpecialistiSpecializariDto specialistiSpecializariDto) {
        SpecialistiSpecializari existingSpecialistiSpecializari = specialistiSpecializariRepository.findById(id).orElse(null);
        if (existingSpecialistiSpecializari != null) {
            specialistiSpecializariDto.setId(id);
            SpecialistiSpecializari updatedSpecialistiSpecializari = specialistiSpecializariRepository.save(SpecialistiSpecializariMapper.mapToSpecialistiSpecializari(specialistiSpecializariDto));
            return SpecialistiSpecializariMapper.mapToSpecialistiSpecializariDto(updatedSpecialistiSpecializari);
        }
        return null;
    }

    @Override
    public void deleteSpecialistiSpecializari(Long id) {
        specialistiSpecializariRepository.deleteById(id);
    }
}