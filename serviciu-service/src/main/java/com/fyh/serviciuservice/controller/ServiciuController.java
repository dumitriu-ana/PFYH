package com.fyh.serviciuservice.controller;

import com.fyh.serviciuservice.dto.ServiciuDto;
import com.fyh.serviciuservice.service.ServiciuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicii")
public class ServiciuController {

    private final ServiciuService serviciuService;

    public ServiciuController(ServiciuService serviciuService) {
        this.serviciuService = serviciuService;
    }

    @PostMapping
    public ResponseEntity<ServiciuDto> createServiciu(@RequestBody ServiciuDto serviciuDto) {
        ServiciuDto savedServiciu = serviciuService.createServiciu(serviciuDto);
        return new ResponseEntity<>(savedServiciu, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiciuDto> getServiciuById(@PathVariable Long id) {
        ServiciuDto serviciuDto = serviciuService.getServiciuById(id);
        if (serviciuDto != null) {
            return new ResponseEntity<>(serviciuDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<ServiciuDto>> getAllServicii() {
        List<ServiciuDto> servicii = serviciuService.getAllServicii();
        return new ResponseEntity<>(servicii, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiciuDto> updateServiciu(@PathVariable Long id, @RequestBody ServiciuDto serviciuDto) {
        ServiciuDto updatedServiciu = serviciuService.updateServiciu(id, serviciuDto);
        if (updatedServiciu != null) {
            return new ResponseEntity<>(updatedServiciu, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiciu(@PathVariable Long id) {
        serviciuService.deleteServiciu(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}