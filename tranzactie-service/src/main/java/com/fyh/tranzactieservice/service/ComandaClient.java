package com.fyh.tranzactieservice.service;

import com.fyh.tranzactieservice.dto.ComandaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8081", value = "comanda-service")
public interface ComandaClient {
    @GetMapping("/api/comenzi/{id}")
    ComandaDto getComandaById(@PathVariable("id") Long id);
}
