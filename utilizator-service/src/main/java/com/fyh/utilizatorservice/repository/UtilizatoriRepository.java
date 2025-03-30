package com.fyh.utilizatorservice.repository;

import com.fyh.utilizatorservice.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilizatoriRepository extends JpaRepository<Utilizator, Long> {
}
