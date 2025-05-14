package com.fyh.utilizatorservice.dto;

public record RegisterRequest(
        String nume,
        String email,
        String password
) {}
