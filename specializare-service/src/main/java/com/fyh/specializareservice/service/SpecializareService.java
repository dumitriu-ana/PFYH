package com.fyh.specializareservice.service;



import com.fyh.specializareservice.dto.SpecializareDto;

import java.util.List;

public interface SpecializareService {
    SpecializareDto createSpecializare(SpecializareDto specializareDto);
    SpecializareDto getSpecializareById(Long id);
    List<SpecializareDto> getAllSpecializari();
    SpecializareDto updateSpecializare(Long id, SpecializareDto specializareDto);
    void deleteSpecializare(Long id);
}