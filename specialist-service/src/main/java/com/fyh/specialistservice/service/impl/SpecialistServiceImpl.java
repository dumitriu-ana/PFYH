package com.fyh.specialistservice.service.impl;


import com.fyh.specialistservice.dto.SpecialistDto;
import com.fyh.specialistservice.entity.Specialist;
import com.fyh.specialistservice.mapper.SpecialistMapper;
import com.fyh.specialistservice.repository.SpecialistRepository;
import com.fyh.specialistservice.service.SpecialistService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialistServiceImpl implements SpecialistService {


    private final SpecialistRepository specialistRepository;

    private RestTemplate restTemplate;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository, RestTemplate restTemplate) {
        this.specialistRepository = specialistRepository;
        this.restTemplate =  restTemplate;
    }

    @Override
    public SpecialistDto createSpecialist(SpecialistDto specialistDto) {
        Long idUtilizator = specialistDto.getIdUtilizator();
        if (idUtilizator != null) {
            // Verifică dacă utilizatorul există în utilizator-service
            String utilizatorServiceUrl = "http://localhost:8088/api/utilizatori/" + idUtilizator;
            ResponseEntity<Void> response = restTemplate.getForEntity(utilizatorServiceUrl, Void.class);
            if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new IllegalArgumentException("Utilizatorul cu ID-ul " + idUtilizator + " nu există.");
            }
        }

        Specialist specialist = SpecialistMapper.mapToSpecialist(specialistDto);
        Specialist savedSpecialist = specialistRepository.save(specialist);
        return SpecialistMapper.mapToSpecialistDto(savedSpecialist);
    }

    @Override
    public SpecialistDto getSpecialistById(Long id) {
        return specialistRepository.findById(id)
                .map(SpecialistMapper::mapToSpecialistDto)
                .orElse(null);
    }

    @Override
    public List<SpecialistDto> getAllSpecialisti() {
        List<Specialist> specialisti = specialistRepository.findAll();
        return specialisti.stream()
                .map(SpecialistMapper::mapToSpecialistDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialistDto updateSpecialist(Long id, SpecialistDto specialistDto) {
        Specialist existingSpecialist = specialistRepository.findById(id).orElse(null);
        if (existingSpecialist != null) {
            specialistDto.setId(id);
            Specialist updatedSpecialist = specialistRepository.save(SpecialistMapper.mapToSpecialist(specialistDto));
            return SpecialistMapper.mapToSpecialistDto(updatedSpecialist);
        }
        return null;
    }

    @Override
    public void deleteSpecialist(Long id) {
        specialistRepository.deleteById(id);
    }
}