package com.fyh.specialistservice.service;


import com.fyh.specialistservice.dto.SpecialistDto;

import java.util.List;

public interface SpecialistService {
    SpecialistDto createSpecialist(SpecialistDto specialistDto);
    SpecialistDto getSpecialistById(Long id);
    List<SpecialistDto> getAllSpecialisti();
    SpecialistDto updateSpecialist(Long id, SpecialistDto specialistDto);
    void deleteSpecialist(Long id);
}