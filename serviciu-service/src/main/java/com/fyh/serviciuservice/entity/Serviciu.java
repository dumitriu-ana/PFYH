package com.fyh.serviciuservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Servicii")
public class Serviciu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_specializare")
    private Long idSpecializare;

    @Column(name = "titlu", nullable = false)
    private String titlu;

    @Column(name = "tip_serviciu")
    private String tipServiciu;

    @Column(name = "pret")
    private BigDecimal pret;

    @Column(name = "durata_rezolvare")
    private Duration durataRezolvare;

    @Column(name = "durata_sedinta")
    private Duration durataSedinta;

    @Column(name = "numar_max_caractere")
    private Integer numarMaxCaractere;
}