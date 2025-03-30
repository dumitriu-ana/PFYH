package com.fyh.utilizatorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtilizatorDto {
    private Long id;
    private String idFirebase;
    private java.sql.Timestamp dataInreg;
    private String nume;
    private String email;
    private String parola;
    private String tipUtilizator;
    private java.math.BigDecimal sold;
}
