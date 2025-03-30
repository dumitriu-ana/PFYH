package com.fyh.specialistspecializareservice.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "Specialisti_Specializari")
public class SpecialistiSpecializari {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_utilizator")
    private Long idSpecialist;

    @Column(name = "id_specializare")
    private Long idSpecializare;

    @Column(unique = true)
    private String unique_for_check;
}