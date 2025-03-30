package com.fyh.tranzactieservice.mapper;


import com.fyh.tranzactieservice.dto.TranzactieDto;
import com.fyh.tranzactieservice.entity.Tranzactie;

public class TranzactieMapper {

    public static TranzactieDto mapToTranzactieDto(Tranzactie tranzactie) {
        if (tranzactie == null) {
            return null;
        }

        TranzactieDto tranzactieDto = new TranzactieDto();
        tranzactieDto.setId(tranzactie.getId());
        tranzactieDto.setIdClient(tranzactie.getIdClient());
        tranzactieDto.setIdSpecialist(tranzactie.getIdSpecialist());
        tranzactieDto.setIdComanda(tranzactie.getIdComanda());
        tranzactieDto.setData(tranzactie.getData());
        tranzactieDto.setComisionProcent(tranzactie.getComisionProcent());
        tranzactieDto.setComisionValoare(tranzactie.getComisionValoare());

        return tranzactieDto;
    }

    public static Tranzactie mapToTranzactie(TranzactieDto tranzactieDto) {
        if (tranzactieDto == null) {
            return null;
        }

        Tranzactie tranzactie = new Tranzactie();
        tranzactie.setId(tranzactieDto.getId());

        // Aici va trebui sa adaugi logica pentru a prelua Utilizator, Specialist si Comanda
        // din repository folosind idClient, idSpecialist si idComanda din tranzactieDto
        // Exemplu:
        // tranzactie.setClient(utilizatorRepository.findById(tranzactieDto.getIdClient()).orElse(null));
        // tranzactie.setSpecialist(specialistRepository.findById(tranzactieDto.getIdSpecialist()).orElse(null));
        // tranzactie.setComanda(comandaRepository.findById(tranzactieDto.getIdComanda()).orElse(null));

        tranzactie.setData(tranzactieDto.getData());
        tranzactie.setComisionProcent(tranzactieDto.getComisionProcent());
        tranzactie.setComisionValoare(tranzactieDto.getComisionValoare());

        return tranzactie;
    }
}