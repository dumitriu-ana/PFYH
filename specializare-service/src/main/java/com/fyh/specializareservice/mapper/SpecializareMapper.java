package com.fyh.specializareservice.mapper;

import com.fyh.specializareservice.dto.SpecializareDto;
import com.fyh.specializareservice.entity.Specializare;


public class SpecializareMapper {

    public static SpecializareDto mapToSpecializareDto(Specializare specializare) {
        if (specializare == null) {
            return null;
        }

        SpecializareDto specializareDto = new SpecializareDto();
        specializareDto.setId(specializare.getId());
        specializareDto.setDenumire(specializare.getDenumire());

        return specializareDto;
    }

    public static Specializare mapToSpecializare(SpecializareDto specializareDto) {
        if (specializareDto == null) {
            return null;
        }

        Specializare specializare = new Specializare();
        specializare.setId(specializareDto.getId());
        specializare.setDenumire(specializareDto.getDenumire());

        return specializare;
    }
}