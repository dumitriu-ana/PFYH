package com.fyh.utilizatorservice.dto;

public record LoginRequest(
        String email,
        String password
) {}
