package com.fyh.specialistserviciuservice.controller;


import com.fyh.specialistserviciuservice.dto.SpecialistiServiciiDto;
import com.fyh.specialistserviciuservice.service.SpecialistiServiciiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialisti-servicii")
@CrossOrigin(origins = "http://localhost:4200") //acces din front
public class SpecialistiServiciiController {

    private final SpecialistiServiciiService specialistiServiciiService;

    public SpecialistiServiciiController(SpecialistiServiciiService specialistiServiciiService) {
        this.specialistiServiciiService = specialistiServiciiService;
    }

    @PostMapping
    public ResponseEntity<SpecialistiServiciiDto> createSpecialistiServicii(@RequestBody SpecialistiServiciiDto specialistiServiciiDto) {
        SpecialistiServiciiDto savedSpecialistiServicii = specialistiServiciiService.createSpecialistiServicii(specialistiServiciiDto);
        return new ResponseEntity<>(savedSpecialistiServicii, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistiServiciiDto> getSpecialistiServiciiById(@PathVariable Long id) {
        SpecialistiServiciiDto specialistiServiciiDto = specialistiServiciiService.getSpecialistiServiciiById(id);
        if (specialistiServiciiDto != null) {
            return new ResponseEntity<>(specialistiServiciiDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SpecialistiServiciiDto>> getAllSpecialistiServicii() {
        List<SpecialistiServiciiDto> specialistiServicii = specialistiServiciiService.getAllSpecialistiServicii();
        return new ResponseEntity<>(specialistiServicii, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialistiServiciiDto> updateSpecialistiServicii(@PathVariable Long id, @RequestBody SpecialistiServiciiDto specialistiServiciiDto) {
        SpecialistiServiciiDto updatedSpecialistiServicii = specialistiServiciiService.updateSpecialistiServicii(id, specialistiServiciiDto);
        if (updatedSpecialistiServicii != null) {
            return new ResponseEntity<>(updatedSpecialistiServicii, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialistiServicii(@PathVariable Long id) {
        specialistiServiciiService.deleteSpecialistiServicii(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}