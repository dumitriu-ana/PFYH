package com.fyh.specialistservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Specialisti")
public class Specialist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_utilizator")
    private Long idUtilizator;
    @Column(name = "id_admin_validare")
    private Long idAdmin;  //utilizator care valideaza atestatul

    @Column(name = "atestat")
    private String atestat;

    @Column(name = "status_validare")
    private String statusValidare;

    @Column(name = "descriere")
    private String descriere;

    @Column(name = "sold_acumulat")
    private String soldAcumulat;

    @Column(name = "data_validare")
    private Timestamp dataValidare;
}