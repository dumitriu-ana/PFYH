package com.fyh.serviciuservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(url = "http://localhost:8083", value = "specialist-service")

public interface SpecialistClient {
    @GetMapping("/api/specialisti/{id}/servicii-ids")
    List<Long> getServiciiIdsForSpecialist(@PathVariable("id") Long id);
}
