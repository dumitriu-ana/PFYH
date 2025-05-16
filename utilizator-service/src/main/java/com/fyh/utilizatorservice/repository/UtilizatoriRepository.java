package com.fyh.utilizatorservice.repository;

import com.fyh.utilizatorservice.entity.Utilizator;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UtilizatoriRepository extends JpaRepository<Utilizator, Long> {
    Optional<Utilizator> findByEmail(String email);
    boolean existsByEmail(String email);
}
