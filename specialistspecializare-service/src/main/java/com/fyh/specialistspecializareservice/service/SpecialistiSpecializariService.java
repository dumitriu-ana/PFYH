package com.fyh.specialistspecializareservice.service;



import com.fyh.specialistspecializareservice.dto.SpecialistiSpecializariDto;

import java.util.List;

public interface SpecialistiSpecializariService {
    SpecialistiSpecializariDto createSpecialistiSpecializari(SpecialistiSpecializariDto specialistiSpecializariDto);
    SpecialistiSpecializariDto getSpecialistiSpecializariById(Long id);
    List<SpecialistiSpecializariDto> getAllSpecialistiSpecializari();
    SpecialistiSpecializariDto updateSpecialistiSpecializari(Long id, SpecialistiSpecializariDto specialistiSpecializariDto);
    void deleteSpecialistiSpecializari(Long id);
}