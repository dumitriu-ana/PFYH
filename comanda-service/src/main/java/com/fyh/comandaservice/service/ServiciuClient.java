package com.fyh.comandaservice.service;

import com.fyh.comandaservice.dto.ServiciuDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "servicii-service", url = "http://localhost:8082")
public interface ServiciuClient {

    @GetMapping("/api/servicii/{id}")
    ServiciuDto getServiciuById(@PathVariable("id") Long id);
}