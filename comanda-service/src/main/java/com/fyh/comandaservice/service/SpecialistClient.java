package com.fyh.comandaservice.service;

import com.fyh.comandaservice.dto.SpecialistDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "specialist-service", url = "http://localhost:8083")
public interface SpecialistClient {

    @GetMapping("/api/specialisti/utilizator/{id}")
    SpecialistDto getSpecialistByUtilizatorId(@PathVariable("id") Long id);

    @GetMapping("/api/specialisti/{id}")
    SpecialistDto getSpecialistById(@PathVariable("id") Long id);
}