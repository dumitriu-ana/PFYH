package com.fyh.serviciuservice.repository;


import com.fyh.serviciuservice.entity.Serviciu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("serviciuRepository")
public interface ServiciuRepository extends JpaRepository<Serviciu, Long> {
}
