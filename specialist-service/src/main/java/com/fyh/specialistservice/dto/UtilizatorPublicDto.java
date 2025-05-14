package com.fyh.specialistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UtilizatorPublicDto {
    private Long id;
    private String nume;
    private String email;
    private String tipUtilizator;
}