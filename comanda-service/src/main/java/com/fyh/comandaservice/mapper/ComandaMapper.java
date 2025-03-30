package com.fyh.comandaservice.mapper;


import com.fyh.comandaservice.dto.ComandaDto;
import com.fyh.comandaservice.entity.Comanda;

public class ComandaMapper {

    public static ComandaDto mapToComandaDto(Comanda comanda) {
        if (comanda == null) {
            return null;
        }

        ComandaDto comandaDto = new ComandaDto();
        comandaDto.setId(comanda.getId());
        comandaDto.setIdClient(comanda.getIdClient() != null ? comanda.getIdClient() : null);
        comandaDto.setIdSpecialist(comanda.getIdSpecialist() != null ? comanda.getIdSpecialist(): null);
        comandaDto.setIdServiciu(comanda.getIdServiciu() != null ? comanda.getIdServiciu() : null);
        comandaDto.setDataCreare(comanda.getDataCreare());
        comandaDto.setStatus(comanda.getStatus());
        comandaDto.setPret(comanda.getPret());
        comandaDto.setMesajClient(comanda.getMesajClient());
        comandaDto.setFisierClient(comanda.getFisierClient());
        comandaDto.setDataSedinta(comanda.getDataSedinta());
        comandaDto.setStatusSedinta(comanda.getStatusSedinta());
        comandaDto.setDataMaximaLivrare(comanda.getDataMaximaLivrare());
        comandaDto.setMesajSpecialist(comanda.getMesajSpecialist());
        comandaDto.setFisierSpecialist(comanda.getFisierSpecialist());

        return comandaDto;
    }

    public static Comanda mapToComanda(ComandaDto comandaDto) {
        if (comandaDto == null) {
            return null;
        }

        Comanda comanda = new Comanda();
        comanda.setId(comandaDto.getId());

        // Aici va trebui sa adaugi logica pentru a prelua Utilizator, Specialist, Serviciu
        // din repository folosind idClient, idSpecialist, idServiciu din comandaDto
        // Exemplu:
        // comanda.setClient(utilizatorRepository.findById(comandaDto.getIdClient()).orElse(null));

        comanda.setDataCreare(comandaDto.getDataCreare());
        comanda.setStatus(comandaDto.getStatus());
        comanda.setPret(comandaDto.getPret());
        comanda.setMesajClient(comandaDto.getMesajClient());
        comanda.setFisierClient(comandaDto.getFisierClient());
        comanda.setDataSedinta(comandaDto.getDataSedinta());
        comanda.setStatusSedinta(comandaDto.getStatusSedinta());
        comanda.setDataMaximaLivrare(comandaDto.getDataMaximaLivrare());
        comanda.setMesajSpecialist(comandaDto.getMesajSpecialist());
        comanda.setFisierSpecialist(comandaDto.getFisierSpecialist());

        return comanda;
    }
}