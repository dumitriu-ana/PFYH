package com.fyh.specialistserviciuservice.mapper;


import com.fyh.specialistserviciuservice.dto.SpecialistiServiciiDto;
import com.fyh.specialistserviciuservice.entity.SpecialistiServicii;

public class SpecialistiServiciiMapper {

    public static SpecialistiServiciiDto mapToSpecialistiServiciiDto(SpecialistiServicii specialistiServicii) {
        if (specialistiServicii == null) {
            return null;
        }

        SpecialistiServiciiDto specialistiServiciiDto = new SpecialistiServiciiDto();
        specialistiServiciiDto.setId(specialistiServicii.getId());
        specialistiServiciiDto.setIdSpecialist(specialistiServicii.getIdSpecialist() != null ? specialistiServicii.getIdSpecialist() : null);
        specialistiServiciiDto.setIdServiciu(specialistiServicii.getIdServiciu() != null ? specialistiServicii.getIdServiciu() : null);

        return specialistiServiciiDto;
    }

    public static SpecialistiServicii mapToSpecialistiServicii(SpecialistiServiciiDto specialistiServiciiDto) {
        if (specialistiServiciiDto == null) {
            return null;
        }

        SpecialistiServicii specialistiServicii = new SpecialistiServicii();
        specialistiServicii.setId(specialistiServiciiDto.getId());


        return specialistiServicii;
    }
}