package com.fyh.comandaservice.dto;

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
public class ComandaDto {

    private Long id;
    private Long idClient;
    private Long idSpecialist;
    private Long idServiciu;
    private Timestamp dataCreare;
    private String status;
    private BigDecimal pret;
    private String mesajClient;
    private String fisierClient;
    private Timestamp dataSedinta;
    private String statusSedinta;
    private Timestamp dataMaximaLivrare;
    private String mesajSpecialist;
    private String fisierSpecialist;
}