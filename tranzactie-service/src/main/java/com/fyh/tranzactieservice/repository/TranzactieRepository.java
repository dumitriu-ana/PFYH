package com.fyh.tranzactieservice.repository;

import com.fyh.tranzactieservice.entity.Tranzactie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TranzactieRepository extends JpaRepository<Tranzactie, Long> {
}
