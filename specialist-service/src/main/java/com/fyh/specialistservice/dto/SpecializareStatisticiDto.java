package com.fyh.specialistservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecializareStatisticiDto {
    private String name;
    private Long value; //nr specialisti
}
