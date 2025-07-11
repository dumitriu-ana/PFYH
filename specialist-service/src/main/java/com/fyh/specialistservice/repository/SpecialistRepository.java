package com.fyh.specialistservice.repository;

import com.fyh.specialistservice.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
    Optional<Specialist> findByIdUtilizator(Long idUtilizator);


}
