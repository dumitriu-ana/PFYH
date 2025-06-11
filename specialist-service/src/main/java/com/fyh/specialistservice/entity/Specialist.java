package com.fyh.specialistservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Specialisti")
public class Specialist {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_utilizator", nullable = false)
    private Long idUtilizator;

    @Column(name = "specializare_id", nullable = false)
    private Long specializareId;

    @ElementCollection
    @CollectionTable(
            name = "specialisti_servicii",
            joinColumns = @JoinColumn(name = "specialist_id")
    )
    @Column(name = "serviciu_id")
    private Set<Long> serviciuIds = new HashSet<>();  // ← nou

    @Column(name = "atestat")            private String atestat;
    @Column(name = "status_validare")    private String statusValidare;
    @Column(name = "descriere")          private String descriere;
    @Column(name = "sold_acumulat")      private String soldAcumulat;
    @Column(name = "id_admin_validare")  private Long idAdmin;
    @Column(name = "data_validare")      private Timestamp dataValidare;
}