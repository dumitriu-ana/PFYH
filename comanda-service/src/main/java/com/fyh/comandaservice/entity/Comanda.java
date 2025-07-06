package com.fyh.comandaservice.entity;

import com.fyh.serviciuservice.entity.Serviciu;
import com.fyh.specialistservice.entity.Specialist;
import com.fyh.utilizatorservice.entity.Utilizator;
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
@Table(name = "Comenzi")
public class Comanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_client")
    private Long idClient;

    @Column(name = "id_specialist")
    private Long idSpecialist;

    @Column(name = "id_serviciu")
    private Long idServiciu;

    @Column(name = "data_creare")
    private Timestamp dataCreare;

    @Column(name = "status")
    private String status;

    @Column(name = "pret")
    private BigDecimal pret;

    @Column(name = "mesaj_client")
    private String mesajClient;

    @Lob
    @Column(name = "fisier_client", columnDefinition = "BYTEA")
    private byte[] fisierClient;

    @Column(name = "nume_fisier_client")
    private String numeFisierClient;

    @Column(name = "data_maxima_livrare")
    private Timestamp dataMaximaLivrare;

    @Column(name = "mesaj_specialist")
    private String mesajSpecialist;

    @Column(name = "nume_fisier_specialist")
    private String numeFisierSpecialist;

    @Lob
    @Column(name = "fisier_specialist", columnDefinition = "BYTEA")
    private byte[] fisierSpecialist;
}