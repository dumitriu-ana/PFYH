package com.fyh.specialistservice.repository;

import com.fyh.specialistservice.entity.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpecialistRepository extends JpaRepository<Specialist, Long> {
}
