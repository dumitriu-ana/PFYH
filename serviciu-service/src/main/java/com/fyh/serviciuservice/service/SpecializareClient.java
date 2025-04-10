package com.fyh.serviciuservice.service;

import com.fyh.specializareservice.dto.SpecializareDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8086", value = "specializare-service")
public interface SpecializareClient {

    @GetMapping("/{id}")
    SpecializareDto getSpecializareById(@PathVariable Long id);
}