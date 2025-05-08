package com.fyh.tranzactieservice.controller;


import com.fyh.tranzactieservice.dto.TranzactieDto;
import com.fyh.tranzactieservice.service.TranzactieService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tranzactii")
@CrossOrigin(origins = "http://localhost:4200") //acces din front
public class TranzactieController {

    private final TranzactieService tranzactieService;

    public TranzactieController(TranzactieService tranzactieService) {
        this.tranzactieService = tranzactieService;
    }

    @PostMapping
    public ResponseEntity<TranzactieDto> createTranzactie(@RequestBody TranzactieDto tranzactieDto) {
        TranzactieDto savedTranzactie = tranzactieService.createTranzactie(tranzactieDto);
        return new ResponseEntity<>(savedTranzactie, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranzactieDto> getTranzactieById(@PathVariable Long id) {
        TranzactieDto tranzactieDto = tranzactieService.getTranzactieById(id);
        if (tranzactieDto != null) {
            return new ResponseEntity<>(tranzactieDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TranzactieDto>> getAllTranzactii() {
        List<TranzactieDto> tranzactii = tranzactieService.getAllTranzactii();
        return new ResponseEntity<>(tranzactii, HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<TranzactieDto> updateTranzactie(@PathVariable Long id, @RequestBody TranzactieDto tranzactieDto) {
        TranzactieDto updatedTranzactie = tranzactieService.updateTranzactie(id, tranzactieDto);
        if (updatedTranzactie != null) {
            return new ResponseEntity<>(updatedTranzactie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranzactie(@PathVariable Long id) {
        tranzactieService.deleteTranzactie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}