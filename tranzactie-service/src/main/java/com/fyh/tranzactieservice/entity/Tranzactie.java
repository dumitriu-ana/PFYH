package com.fyh.tranzactieservice.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name = "Tranzactii")
public class Tranzactie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_client")
    private Long idClient;

    @JoinColumn(name = "id_specialist")
    private Long idSpecialist;

    @JoinColumn(name = "id_comanda")
    private Long idComanda;

    @Column(name = "data")
    private Timestamp data;

    @Column(name = "comision_procent")
    private BigDecimal comisionProcent;

    @Column(name = "comision_valoare")
    private BigDecimal comisionValoare;
}