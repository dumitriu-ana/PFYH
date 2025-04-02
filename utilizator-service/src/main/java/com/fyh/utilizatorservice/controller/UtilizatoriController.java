package com.fyh.utilizatorservice.controller;

import com.fyh.utilizatorservice.dto.UtilizatorDto;
import com.fyh.utilizatorservice.service.UtilizatoriService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utilizatori")
public class UtilizatoriController {
    private final UtilizatoriService utilizatoriService;

    public UtilizatoriController(UtilizatoriService utilizatoriService) {
        this.utilizatoriService = utilizatoriService;
    }

    @PostMapping
    public ResponseEntity<UtilizatorDto> createUtilizatori(@RequestBody UtilizatorDto utilizatorDto) {
        UtilizatorDto savedUtilizatori = utilizatoriService.createUtilizatori(utilizatorDto);
        return new ResponseEntity<>(savedUtilizatori, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UtilizatorDto> getUtilizatoriById(@PathVariable("id") Long id) {
        UtilizatorDto utilizatorDto = utilizatoriService.getUtilizatoriById(id);
        if (utilizatorDto != null) {
            return new ResponseEntity<>(utilizatorDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<UtilizatorDto>> getAllUtilizatori() {
        List<UtilizatorDto> utilizatorDtos = utilizatoriService.getAllUtilizatori();
        return new ResponseEntity<>(utilizatorDtos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UtilizatorDto> updateUtilizatori(@PathVariable("id") Long id, @RequestBody UtilizatorDto utilizatorDto) {
        UtilizatorDto updatedUtilizatori = utilizatoriService.updateUtilizatori(id, utilizatorDto);
        return new ResponseEntity<>(updatedUtilizatori, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUtilizatori(@PathVariable("id") Long id) {
        utilizatoriService.deleteUtilizatori(id);
        return new ResponseEntity<>("Utilizator sters cu succes!", HttpStatus.OK);
    }
}
