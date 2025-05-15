package com.fyh.serviciuservice.service;


import com.fyh.serviciuservice.dto.ServiciuDto;

import java.util.List;

public interface ServiciuService {
    ServiciuDto createServiciu(ServiciuDto serviciuDto);
    ServiciuDto getServiciuById(Long id);
    List<ServiciuDto> getAllServicii();
    ServiciuDto updateServiciu(Long id, ServiciuDto serviciuDto);
    void deleteServiciu(Long id);

    List<ServiciuDto> findBySpecialist(Long idSpecialist);
}