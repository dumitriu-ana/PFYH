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
public class SpecialistDto {
    private Long id;
    private Long idUtilizator;
    private Long specializareId;
    private List<Long> serviciuIds;
    private String atestat;
    private String statusValidare;
    private String descriere;
    private String soldAcumulat;
    private Long idAdmin;
    private Timestamp dataValidare;
}