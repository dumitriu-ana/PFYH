package com.fyh.comandaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiciuDto {

    private Long id;
    private Long idSpecializare;
    private String titlu;
    private String tipServiciu;
    private BigDecimal pret;
    private Duration durataRezolvare;
    private Duration durataSedinta;
    private Integer numarMaxCaractere;
}