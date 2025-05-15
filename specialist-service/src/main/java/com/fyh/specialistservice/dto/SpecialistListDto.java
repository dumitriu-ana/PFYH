package com.fyh.specialistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialistListDto {
    private Long id;
    private Long idUtilizator;
    private String nume; // Numele utilizatorului
    private String atestat;
    private String descriere;

    private List<ServiciuDto> servicii;
}