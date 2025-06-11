package com.fyh.specialistspecializareservice.mapper;


import com.fyh.specialistspecializareservice.dto.SpecialistiSpecializariDto;
import com.fyh.specialistspecializareservice.entity.SpecialistiSpecializari;

public class SpecialistiSpecializariMapper {

    public static SpecialistiSpecializariDto mapToSpecialistiSpecializariDto(SpecialistiSpecializari specialistiSpecializari) {
        if (specialistiSpecializari == null) {
            return null;
        }

        SpecialistiSpecializariDto specialistiSpecializariDto = new SpecialistiSpecializariDto();
        specialistiSpecializariDto.setId(specialistiSpecializari.getId());
        specialistiSpecializariDto.setIdSpecialist(specialistiSpecializari.getIdSpecialist() != null ? specialistiSpecializari.getIdSpecialist() : null);
        specialistiSpecializariDto.setIdSpecializare(specialistiSpecializari.getIdSpecializare()!= null ? specialistiSpecializari.getIdSpecializare() : null);

        return specialistiSpecializariDto;
    }

    public static SpecialistiSpecializari mapToSpecialistiSpecializari(SpecialistiSpecializariDto specialistiSpecializariDto) {
        if (specialistiSpecializariDto == null) {
            return null;
        }

        SpecialistiSpecializari specialistiSpecializari = new SpecialistiSpecializari();
        specialistiSpecializari.setId(specialistiSpecializariDto.getId());

        return specialistiSpecializari;
    }
}