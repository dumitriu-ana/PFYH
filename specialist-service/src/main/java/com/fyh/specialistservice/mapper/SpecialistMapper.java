package com.fyh.specialistservice.mapper;

import com.fyh.specialistservice.dto.SpecialistDto;
import com.fyh.specialistservice.entity.Specialist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpecialistMapper {

    public static SpecialistDto mapToSpecialistDto(Specialist specialist) {
        if (specialist == null) {
            return null;
        }

        SpecialistDto dto = new SpecialistDto();
        dto.setId(specialist.getId());
        dto.setIdUtilizator(specialist.getIdUtilizator());
        dto.setSpecializareId(specialist.getSpecializareId());           // ← nou
        // convertim Set<Long> în List<Long> pentru DTO
        dto.setServiciuIds(new ArrayList<>(specialist.getServiciuIds())); // ← nou

        dto.setAtestat(specialist.getAtestat());
        dto.setStatusValidare(specialist.getStatusValidare());
        dto.setDescriere(specialist.getDescriere());
        dto.setSoldAcumulat(specialist.getSoldAcumulat());
        dto.setIdAdmin(specialist.getIdAdmin());
        dto.setDataValidare(specialist.getDataValidare());
        return dto;
    }

    public static Specialist mapToSpecialist(SpecialistDto dto) {
        if (dto == null) {
            return null;
        }

        Specialist entity = new Specialist();
        entity.setId(dto.getId());
        entity.setIdUtilizator(dto.getIdUtilizator());
        entity.setSpecializareId(dto.getSpecializareId());               // ← nou
        // convertim List<Long> în Set<Long> pentru entitate
        if (dto.getServiciuIds() != null) {
            entity.setServiciuIds(new HashSet<>(dto.getServiciuIds())); // ← nou
        }

        entity.setAtestat(dto.getAtestat());
        entity.setStatusValidare(dto.getStatusValidare());
        entity.setDescriere(dto.getDescriere());
        entity.setSoldAcumulat(dto.getSoldAcumulat());
        entity.setIdAdmin(dto.getIdAdmin());
        entity.setDataValidare(dto.getDataValidare());
        return entity;
    }
}
