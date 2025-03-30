package com.fyh.utilizatorservice.mapper;


import com.fyh.utilizatorservice.dto.UtilizatorDto;
import com.fyh.utilizatorservice.entity.Utilizator;

public class UtilizatorMapper {
    public static UtilizatorDto mapToUtilizatoriDto(Utilizator utilizator) {
        return new UtilizatorDto(
                utilizator.getId(),
                utilizator.getIdFirebase(),
                utilizator.getDataInregistrare(),
                utilizator.getNume(),
                utilizator.getEmail(),
                utilizator.getParola(),
                utilizator.getTipUtilizator(),
                utilizator.getSold()
        );
    }

    public static Utilizator mapToUtilizatori(UtilizatorDto utilizatorDto) {
        return new Utilizator(
                utilizatorDto.getId(),
                utilizatorDto.getIdFirebase(),
                utilizatorDto.getDataInreg(),
                utilizatorDto.getNume(),
                utilizatorDto.getEmail(),
                utilizatorDto.getParola(),
                utilizatorDto.getTipUtilizator(),
                utilizatorDto.getSold()
        );
    }
}
