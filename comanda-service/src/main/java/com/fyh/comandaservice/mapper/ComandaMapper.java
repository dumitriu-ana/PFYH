package com.fyh.comandaservice.mapper;


import com.fyh.comandaservice.dto.ComandaDto;
import com.fyh.comandaservice.entity.Comanda;

import java.sql.Timestamp;

public class ComandaMapper {

    public static ComandaDto mapToComandaDto(Comanda comanda) {
        if (comanda == null) {
            return null;
        }

        ComandaDto comandaDto = new ComandaDto();
        comandaDto.setId(comanda.getId());
        comandaDto.setIdClient(comanda.getIdClient() != null ? comanda.getIdClient() : null);
        comandaDto.setIdSpecialist(comanda.getIdSpecialist() != null ? comanda.getIdSpecialist() : null);
        comandaDto.setIdServiciu(comanda.getIdServiciu() != null ? comanda.getIdServiciu() : null);
        comandaDto.setDataCreare(comanda.getDataCreare());
        comandaDto.setStatus(comanda.getStatus());
        comandaDto.setPret(comanda.getPret());
        comandaDto.setMesajClient(comanda.getMesajClient());
        comandaDto.setFisierClient(comanda.getFisierClient());
        comandaDto.setDataMaximaLivrare(comanda.getDataMaximaLivrare());
        comandaDto.setMesajSpecialist(comanda.getMesajSpecialist());
        comandaDto.setFisierSpecialist(comanda.getFisierSpecialist());

        return comandaDto;
    }

    public static Comanda mapToComanda(ComandaDto comandaDto) {
        if (comandaDto == null) return null;

        Comanda comanda = new Comanda();
        comanda.setId(comandaDto.getId());
        comanda.setIdClient(comandaDto.getIdClient());
        comanda.setIdSpecialist(comandaDto.getIdSpecialist());
        comanda.setIdServiciu(comandaDto.getIdServiciu());
        comanda.setDataCreare(comandaDto.getDataCreare());
        comanda.setStatus(comandaDto.getStatus());
        comanda.setPret(comandaDto.getPret());
        comanda.setMesajClient(comandaDto.getMesajClient());
        comanda.setFisierClient(comandaDto.getFisierClient());
        comanda.setNumeFisierClient(comandaDto.getNumeFisierClient());

        // data maxima livrare = plasare + 5 zile
        if (comandaDto.getDataCreare() != null) {
            comanda.setDataMaximaLivrare(
                    new Timestamp(comandaDto.getDataCreare().getTime() + 5L * 24 * 60 * 60 * 1000)
            );
        }

        comanda.setMesajSpecialist(comandaDto.getMesajSpecialist());
        comanda.setFisierSpecialist(comandaDto.getFisierSpecialist());
        comanda.setNumeFisierSpecialist(comandaDto.getNumeFisierSpecialist());

        return comanda;
    }
}
