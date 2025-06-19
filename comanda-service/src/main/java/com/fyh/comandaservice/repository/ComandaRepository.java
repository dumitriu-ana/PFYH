package com.fyh.comandaservice.repository;


import com.fyh.comandaservice.entity.Comanda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComandaRepository extends JpaRepository<Comanda, Long> {
    List<Comanda> findByIdClient(Long idClient);

}
