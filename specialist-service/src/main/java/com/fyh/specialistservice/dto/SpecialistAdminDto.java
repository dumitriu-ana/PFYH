package com.fyh.specialistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistAdminDto {
    private Long id;
    private Long idUtilizator;
    private String numeUtilizator;
    private String emailUtilizator;
    private String tipUtilizator;

    private Long specializareId;
    private String denumireSpecializare;

    private List<ServiciuDto> servicii;
    private String atestat;
    private String descriere;
    private String statusValidare;
    private String soldAcumulat;
    private Long idAdmin;
    private Timestamp dataValidare;
}
