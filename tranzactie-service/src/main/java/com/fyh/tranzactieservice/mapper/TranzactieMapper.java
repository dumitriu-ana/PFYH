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
        tranzactieDto.setValoare(tranzactie.getValoare());

        return tranzactieDto;
    }

    public static Tranzactie mapToTranzactie(TranzactieDto tranzactieDto) {
        if (tranzactieDto == null) {
            return null;
        }

        Tranzactie tranzactie = new Tranzactie();
        tranzactie.setId(tranzactieDto.getId());

        tranzactie.setIdClient(tranzactieDto.getIdClient());
        tranzactie.setIdSpecialist(tranzactieDto.getIdSpecialist());
        tranzactie.setIdComanda(tranzactieDto.getIdComanda());

        tranzactie.setData(tranzactieDto.getData());
        tranzactie.setComisionProcent(tranzactieDto.getComisionProcent());
        tranzactie.setValoare(tranzactieDto.getValoare());

        return tranzactie;
    }
}