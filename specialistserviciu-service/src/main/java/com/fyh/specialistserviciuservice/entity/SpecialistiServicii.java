package com.fyh.specialistserviciuservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Specialisti_Servicii")
public class SpecialistiServicii {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "id_utilizator")
    private Long idSpecialist;

    @JoinColumn(name = "id_serviciu")
    private Long idServiciu;
}