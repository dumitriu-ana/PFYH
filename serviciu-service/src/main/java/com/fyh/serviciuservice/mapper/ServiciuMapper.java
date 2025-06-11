package com.fyh.serviciuservice.mapper;


import com.fyh.serviciuservice.dto.ServiciuDto;
import com.fyh.serviciuservice.entity.Serviciu;

public class ServiciuMapper {

    public static ServiciuDto mapToServiciuDto(Serviciu serviciu) {
        if (serviciu == null) {
            return null;
        }

        ServiciuDto serviciuDto = new ServiciuDto();
        serviciuDto.setId(serviciu.getId());
        serviciuDto.setIdSpecializare(serviciu.getIdSpecializare() != null ? serviciu.getIdSpecializare() : null);
        serviciuDto.setTitlu(serviciu.getTitlu());
        serviciuDto.setTipServiciu(serviciu.getTipServiciu());
        serviciuDto.setPret(serviciu.getPret());
        serviciuDto.setDurataRezolvare(serviciu.getDurataRezolvare());
        serviciuDto.setDurataSedinta(serviciu.getDurataSedinta());
        serviciuDto.setNumarMaxCaractere(serviciu.getNumarMaxCaractere());

        return serviciuDto;
    }

    public static Serviciu mapToServiciu(ServiciuDto serviciuDto) {
        if (serviciuDto == null) {
            return null;
        }

        Serviciu serviciu = new Serviciu();
        serviciu.setId(serviciuDto.getId());
        serviciu.setIdSpecializare(serviciuDto.getIdSpecializare());
        serviciu.setTitlu(serviciuDto.getTitlu());
        serviciu.setTipServiciu(serviciuDto.getTipServiciu());
        serviciu.setPret(serviciuDto.getPret());
        serviciu.setDurataRezolvare(serviciuDto.getDurataRezolvare());
        serviciu.setDurataSedinta(serviciuDto.getDurataSedinta());
        serviciu.setNumarMaxCaractere(serviciuDto.getNumarMaxCaractere());

        return serviciu;
    }
}