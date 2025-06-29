package com.fyh.tranzactieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TranzactieDto {

    private Long id;
    private Long idClient; //utilizatorul logat care comanda
    private Long idSpecialist;
    private Long idComanda;
    private Timestamp data;
    private BigDecimal comisionProcent; //10% din valoarea serviciului
    private BigDecimal valoare; // pret serviciu
}