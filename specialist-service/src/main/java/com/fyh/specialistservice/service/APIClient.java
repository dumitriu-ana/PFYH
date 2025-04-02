package com.fyh.specialistservice.service;

import com.fyh.specialistservice.feign.CustomErrorDecoder;
import com.fyh.utilizatorservice.dto.UtilizatorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(url = "http://localhost:8088", value = "UTILIZATORI-SERVICE", configuration = CustomErrorDecoder.class)
public interface APIClient {

    @GetMapping("/api/utilizatori/{id}")
    UtilizatorDto getUtilizatoriById(@PathVariable("id") Long id);
}
