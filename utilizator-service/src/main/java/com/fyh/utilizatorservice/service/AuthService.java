package com.fyh.utilizatorservice.service;

import com.fyh.utilizatorservice.dto.*;
import com.fyh.utilizatorservice.entity.Utilizator;
import com.fyh.utilizatorservice.mapper.UtilizatorMapper;
import com.fyh.utilizatorservice.repository.UtilizatoriRepository;
import com.fyh.utilizatorservice.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UtilizatoriRepository repo;
    private final PasswordEncoder encoder;
    private final JwtTokenProvider jwt;

    public AuthService(UtilizatoriRepository repo,
                       PasswordEncoder encoder,
                       JwtTokenProvider jwt) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public AuthResponse register(RegisterRequest req) {
        // 1) unicitate email
        if (repo.existsByEmail(req.email())) {
            throw new IllegalArgumentException(
                    "Adresa de email introdusa este deja folosita. Introdu o alta adresa."
            );
        }
        // 2) salvează utilizatorul
        Utilizator u = new Utilizator();
        u.setNume(req.nume());
        u.setEmail(req.email());
        u.setParola(encoder.encode(req.password()));
        u.setTipUtilizator("client");
        Utilizator saved = repo.save(u);

        // 3) emite JWT
        String token = jwt.createToken(saved.getEmail(), saved.getTipUtilizator());
        // 4) construieşte DTO-ul
        UtilizatorDto userDto = UtilizatorMapper.mapToUtilizatoriDto(saved);

        // 5) întoarce răspunsul complet
        return new AuthResponse(token, userDto);
    }

    public AuthResponse login(LoginRequest req) {
        Utilizator u = repo.findByEmail(req.email())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Credentialele folosite sunt invalide. Incearca din nou."
                ));
        if (!encoder.matches(req.password(), u.getParola())) {
            throw new IllegalArgumentException(
                    "Credentialele folosite sunt invalide. Incearca din nou."
            );
        }
        String token = jwt.createToken(u.getEmail(), u.getTipUtilizator());
        UtilizatorDto userDto = UtilizatorMapper.mapToUtilizatoriDto(u);
        return new AuthResponse(token, userDto);
    }
}
