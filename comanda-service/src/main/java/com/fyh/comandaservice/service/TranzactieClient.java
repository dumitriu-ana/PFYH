package com.fyh.comandaservice.service;

import com.fyh.comandaservice.dto.TranzactieDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "tranzactii-service", url = "http://localhost:8087")
public interface TranzactieClient {

    @PostMapping("/api/tranzactii")
    void createTranzactie(@RequestBody TranzactieDto tranzactieDto);
}
