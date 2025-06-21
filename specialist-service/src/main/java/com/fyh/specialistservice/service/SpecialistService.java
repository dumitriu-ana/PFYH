package com.fyh.specialistservice.service;


import com.fyh.specialistservice.dto.SpecialistDto;
import com.fyh.specialistservice.dto.SpecializareDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SpecialistService {
    SpecialistDto createSpecialist(SpecialistDto specialistDto);
    SpecialistDto getSpecialistById(Long id);
    List<SpecialistDto> getAllSpecialisti();

    SpecialistDto getSpecialistByUtilizatorId(Long idUtilizator);

    SpecialistDto updateSpecialist(Long id, SpecialistDto specialistDto);
    void deleteSpecialist(Long id);

    ResponseEntity<String> addSpecializareToSpecialist(Long idUtilizator, Long idSpecializare);

    ResponseEntity<List<SpecializareDto>> getSpecializariForSpecialist(Long idUtilizator);

    SpecialistDto validateSpecialist(Long id, Long adminId);

    SpecialistDto validateAndPromote(Long specialistId, Long adminId);

    SpecialistDto addServiciuToSpecialist(Long specialistId, Long serviciuId);

    SpecialistDto removeServiciuFromSpecialist(Long id, Long serviciuId);

}