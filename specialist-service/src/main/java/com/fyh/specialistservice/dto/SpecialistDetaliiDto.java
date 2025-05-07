package com.fyh.specialistservice.dto;

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
public class SpecialistDetaliiDto {
    // Câmpuri din Specialist
    private Long id;
    private Long idUtilizator;
    private String atestat;
    private String statusValidare;
    private String descriere;
    private String soldAcumulat;
    private Long idAdmin;
    private Timestamp dataValidare;

    // Câmpuri din Utilizator
    private String idFirebase;
    private Timestamp dataInreg;
    private String nume;
    private String email;
    private String tipUtilizator;
    private BigDecimal sold;
}
