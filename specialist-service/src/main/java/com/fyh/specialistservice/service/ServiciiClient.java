package com.fyh.specialistservice.service;

import com.fyh.specialistservice.dto.ServiciuDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "serviciu-service", url = "http://localhost:8082")
public interface ServiciiClient {
    @GetMapping("/api/servicii/specialisti/{idSpecialist}")
    List<ServiciuDto> getServiciiBySpecialist(@PathVariable Long idSpecialist);
}
