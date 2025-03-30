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

        // Aici va trebui sa adaugi logica pentru a prelua Specialist si Serviciu
        // din repository folosind idSpecialist si idServiciu din specialistiServiciiDto
        // Exemplu:
        // specialistiServicii.setSpecialist(specialistRepository.findById(specialistiServiciiDto.getIdSpecialist()).orElse(null));
        // specialistiServicii.setServiciu(serviciuRepository.findById(specialistiServiciiDto.getIdServiciu()).orElse(null));

        return specialistiServicii;
    }
}