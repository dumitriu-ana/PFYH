package com.fyh.comandaservice.service;

import com.fyh.comandaservice.dto.UtilizatorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.fyh.comandaservice.dto.SoldUpdateDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:8088", value = "UTILIZATORI-SERVICE")
public interface UtilizatorClient {

    @GetMapping("/api/utilizatori/{id}")
    UtilizatorDto getUtilizatoriById(@PathVariable("id") Long id);

    @PostMapping("/api/utilizatori/{id}/adauga-sold")
    void adaugaLaSold(@PathVariable("id") Long id, @RequestBody SoldUpdateDto soldUpdateDto);
}
