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
public class UtilizatorDto {
    private Long id;
    private String idFirebase;
    private Timestamp dataInreg;
    private String nume;
    private String email;
    private String parola;
    private String tipUtilizator;
    private BigDecimal sold;
}