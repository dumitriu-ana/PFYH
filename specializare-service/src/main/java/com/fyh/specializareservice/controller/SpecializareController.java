package com.fyh.specializareservice.controller;


import com.fyh.specializareservice.dto.SpecializareDto;
import com.fyh.specializareservice.service.SpecializareService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specializari")
public class SpecializareController {

    private final SpecializareService specializareService;

    public SpecializareController(SpecializareService specializareService) {
        this.specializareService = specializareService;
    }

    @PostMapping
    public ResponseEntity<SpecializareDto> createSpecializare(@RequestBody SpecializareDto specializareDto) {
        SpecializareDto savedSpecializare = specializareService.createSpecializare(specializareDto);
        return new ResponseEntity<>(savedSpecializare, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecializareDto> getSpecializareById(@PathVariable Long id) {
        SpecializareDto specializareDto = specializareService.getSpecializareById(id);
        if (specializareDto != null) {
            return new ResponseEntity<>(specializareDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SpecializareDto>> getAllSpecializari() {
        List<SpecializareDto> specializari = specializareService.getAllSpecializari();
        return new ResponseEntity<>(specializari, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecializareDto> updateSpecializare(@PathVariable Long id, @RequestBody SpecializareDto specializareDto) {
        SpecializareDto updatedSpecializare = specializareService.updateSpecializare(id, specializareDto);
        if (updatedSpecializare != null) {
            return new ResponseEntity<>(updatedSpecializare, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecializare(@PathVariable Long id) {
        specializareService.deleteSpecializare(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}