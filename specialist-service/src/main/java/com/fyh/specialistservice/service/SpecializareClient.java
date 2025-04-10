package com.fyh.specialistservice.service;

import com.fyh.specialistservice.dto.SpecializareDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "specializare-service", url = "http://localhost:8086")
public interface SpecializareClient {

    @GetMapping("/api/specializari/{id}")
    SpecializareDto getSpecializareById(@PathVariable("id") Long id);
}