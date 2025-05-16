package com.fyh.utilizatorservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private UtilizatorDto user;      // DTO-ul existent cu câmpurile id, nume, email, etc.
}
