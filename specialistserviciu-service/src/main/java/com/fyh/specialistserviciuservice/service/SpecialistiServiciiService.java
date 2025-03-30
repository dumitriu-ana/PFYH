package com.fyh.specialistserviciuservice.service;



import com.fyh.specialistserviciuservice.dto.SpecialistiServiciiDto;

import java.util.List;

public interface SpecialistiServiciiService {
    SpecialistiServiciiDto createSpecialistiServicii(SpecialistiServiciiDto specialistiServiciiDto);
    SpecialistiServiciiDto getSpecialistiServiciiById(Long id);
    List<SpecialistiServiciiDto> getAllSpecialistiServicii();
    SpecialistiServiciiDto updateSpecialistiServicii(Long id, SpecialistiServiciiDto specialistiServiciiDto);
    void deleteSpecialistiServicii(Long id);
}