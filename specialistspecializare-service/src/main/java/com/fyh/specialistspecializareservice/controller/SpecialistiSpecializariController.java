package com.fyh.specialistspecializareservice.controller;


import com.fyh.specialistspecializareservice.dto.SpecialistiSpecializariDto;
import com.fyh.specialistspecializareservice.service.SpecialistiSpecializariService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/specialisti-specializari")
public class SpecialistiSpecializariController {

    private final SpecialistiSpecializariService specialistiSpecializariService;

    public SpecialistiSpecializariController(SpecialistiSpecializariService specialistiSpecializariService) {
        this.specialistiSpecializariService = specialistiSpecializariService;
    }

    @PostMapping
    public ResponseEntity<SpecialistiSpecializariDto> createSpecialistiSpecializari(@RequestBody SpecialistiSpecializariDto specialistiSpecializariDto) {
        SpecialistiSpecializariDto savedSpecialistiSpecializari = specialistiSpecializariService.createSpecialistiSpecializari(specialistiSpecializariDto);
        return new ResponseEntity<>(savedSpecialistiSpecializari, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistiSpecializariDto> getSpecialistiSpecializariById(@PathVariable Long id) {
        SpecialistiSpecializariDto specialistiSpecializariDto = specialistiSpecializariService.getSpecialistiSpecializariById(id);
        if (specialistiSpecializariDto != null) {
            return new ResponseEntity<>(specialistiSpecializariDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SpecialistiSpecializariDto>> getAllSpecialistiSpecializari() {
        List<SpecialistiSpecializariDto> specialistiSpecializari = specialistiSpecializariService.getAllSpecialistiSpecializari();
        return new ResponseEntity<>(specialistiSpecializari, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialistiSpecializariDto> updateSpecialistiSpecializari(@PathVariable Long id, @RequestBody SpecialistiSpecializariDto specialistiSpecializariDto) {
        SpecialistiSpecializariDto updatedSpecialistiSpecializari = specialistiSpecializariService.updateSpecialistiSpecializari(id, specialistiSpecializariDto);
        if (updatedSpecialistiSpecializari != null) {
            return new ResponseEntity<>(updatedSpecialistiSpecializari, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialistiSpecializari(@PathVariable Long id) {
        specialistiSpecializariService.deleteSpecialistiSpecializari(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}