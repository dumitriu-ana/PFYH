package com.fyh.specialistservice.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "specialisti-specializari-service", url = "http://localhost:8085")
public interface SpecialistiSpecializariClient {

    @PostMapping("/api/specialisti-specializari")
    ResponseEntity<Void> addSpecialistSpecializare(@RequestBody Map<String, Long> payload);

    @GetMapping("/api/specialisti/{idSpecialist}/specializari")
    List<Long> getSpecializariForSpecialist(@PathVariable("idSpecialist") Long idSpecialist);
}