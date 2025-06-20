package com.fyh.comandaservice.controller;


import com.fyh.comandaservice.dto.ComandaDto;
import com.fyh.comandaservice.service.ComandaService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/comenzi")
@CrossOrigin(origins = "http://localhost:4200") //acces din front
public class ComandaController {

    private final ComandaService comandaService;

    public ComandaController(ComandaService comandaService) {
        this.comandaService = comandaService;
    }

    @PostMapping
    public ResponseEntity<ComandaDto> createComanda(@RequestBody ComandaDto comandaDto) {
        ComandaDto savedComanda = comandaService.createComanda(comandaDto);
        return new ResponseEntity<>(savedComanda, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComandaDto> getComandaById(@PathVariable Long id) {
        ComandaDto comandaDto = comandaService.getComandaById(id);
        if (comandaDto != null) {
            return new ResponseEntity<>(comandaDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ComandaDto>> getAllComenzi() {
        List<ComandaDto> comenzi = comandaService.getAllComenzi();
        return new ResponseEntity<>(comenzi, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ComandaDto> updateComanda(@PathVariable Long id, @RequestBody ComandaDto comandaDto) {
        ComandaDto updatedComanda = comandaService.updateComanda(id, comandaDto);
        if (updatedComanda != null) {
            return new ResponseEntity<>(updatedComanda, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComanda(@PathVariable Long id) {
        comandaService.deleteComanda(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/cu-fisier")
    public ResponseEntity<ComandaDto> createComandaCuFisier(
            @RequestPart("comanda") ComandaDto comandaDto,
            @RequestPart(value = "fisier", required = false) MultipartFile fisier
    ) {
        if (fisier != null && !fisier.isEmpty()) {
            try {
                comandaDto.setFisierClient(fisier.getBytes());
                comandaDto.setNumeFisierClient(fisier.getOriginalFilename());
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }

        ComandaDto saved = comandaService.createComanda(comandaDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<ComandaDto>> getComenziByClientId(@PathVariable Long clientId) {
        List<ComandaDto> comenzi = comandaService.getComenziByClientId(clientId);
        return ResponseEntity.ok(comenzi);
    }


    //desc fisiere
    @GetMapping("/{id}/fisier/client")
    public ResponseEntity<Resource> downloadFisierClient(@PathVariable Long id) {
        ComandaDto comanda = comandaService.getComandaById(id);
        if (comanda == null || comanda.getFisierClient() == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(comanda.getFisierClient());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + comanda.getNumeFisierClient() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM) // Tip generic pentru fisier
                .body(resource);
    }

    @GetMapping("/{id}/fisier/specialist")
    public ResponseEntity<Resource> downloadFisierSpecialist(@PathVariable Long id) {
        ComandaDto comanda = comandaService.getComandaById(id);
        if (comanda == null || comanda.getFisierSpecialist() == null) {
            return ResponseEntity.notFound().build();
        }

        ByteArrayResource resource = new ByteArrayResource(comanda.getFisierSpecialist());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + comanda.getNumeFisierSpecialist() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }
}