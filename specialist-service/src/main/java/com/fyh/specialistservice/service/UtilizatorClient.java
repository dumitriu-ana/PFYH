package com.fyh.specialistservice.service;

import com.fyh.specialistservice.dto.UtilizatorPublicDto;
import com.fyh.specialistservice.feign.CustomErrorDecoder;
import com.fyh.specialistservice.dto.UtilizatorDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(url = "http://localhost:8088", value = "UTILIZATORI-SERVICE", configuration = CustomErrorDecoder.class)
public interface UtilizatorClient {

    @GetMapping("/api/utilizatori/{id}")
    UtilizatorDto getUtilizatoriById(@PathVariable("id") Long id);

    @GetMapping("api/utilizatori/{id}/public")
    UtilizatorPublicDto getPublicUtilizatorById(@PathVariable("id") Long id);

    @PutMapping("/api/utilizatori/{id}/tip")
    void changeTipUtilizator(@PathVariable Long id, @RequestBody Map<String,String> payload);
}
