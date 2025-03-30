package com.fyh.utilizatorservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity   //has a jpa ent
@Table(name = "Utilizatori")
public class Utilizator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_firebase")
    private String idFirebase;
    @Column(name = "data_inreg")
    private java.sql.Timestamp dataInregistrare;
    @Column(name = "nume")
    private String nume;
    @Column(name = "email")
    private String email;
    @Column(name = "parola")
    private String parola;
    @Column(name = "tip_utilizator")
    private String tipUtilizator;
    @Column(name = "sold")
    private java.math.BigDecimal sold;
}
