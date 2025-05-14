package com.fyh.utilizatorservice.service;

import com.fyh.utilizatorservice.dto.*;
import com.fyh.utilizatorservice.entity.Utilizator;
import com.fyh.utilizatorservice.mapper.UtilizatorMapper;
import com.fyh.utilizatorservice.repository.UtilizatoriRepository;
import com.fyh.utilizatorservice.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Service
public class AuthService {
    private final UtilizatoriRepository repo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwt;

    public AuthService(UtilizatoriRepository repo, PasswordEncoder encoder, JwtTokenProvider jwt) {
        this.repo = repo; this.encoder = encoder; this.jwt = jwt;
    }

    public AuthResponse register(RegisterRequest req) {
        if (repo.findByEmail(req.email()).isPresent())
            throw new RuntimeException("Email folosit");
        Utilizator u = new Utilizator();
        u.setNume(req.nume());
        u.setEmail(req.email());
        u.setParola(encoder.encode(req.password()));
        u.setTipUtilizator("client");
        u.setSold(BigDecimal.ZERO);
        u.setDataInregistrare(new Timestamp(System.currentTimeMillis()));
        repo.save(u);
        String token = jwt.createToken(u.getEmail(), u.getTipUtilizator());
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest req) {
        var u = repo.findByEmail(req.email())
                .orElseThrow(() -> new RuntimeException("Credențiale greșite"));
        if (!encoder.matches(req.password(), u.getParola()))
            throw new RuntimeException("Credențiale greșite");
        String token = jwt.createToken(u.getEmail(), u.getTipUtilizator());
        return new AuthResponse(token);
    }
}
