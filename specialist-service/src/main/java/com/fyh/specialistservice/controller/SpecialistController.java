package com.fyh.specialistservice.controller;


import com.fyh.specialistservice.dto.*;
import com.fyh.specialistservice.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/specialisti")
@CrossOrigin(origins = "http://localhost:4200") //acces din front
public class SpecialistController {

    private final SpecialistService specialistService;
    private final SpecialistiSpecializariClient specialistiSpecializariClient;
    private final SpecializareClient specializareClient;

    private final UtilizatorClient utilizatorClient;
    private final ServiciiClient serviciiClient;
    public SpecialistController(SpecialistService specialistService,
                                SpecialistiSpecializariClient specialistiSpecializariClient,
                                SpecializareClient specializareClient, UtilizatorClient utilizatorClient, ServiciiClient serviciiClient) {
        this.specialistService = specialistService;
        this.specialistiSpecializariClient = specialistiSpecializariClient;
        this.specializareClient = specializareClient;
        this.utilizatorClient = utilizatorClient;
        this.serviciiClient = serviciiClient;
    }

    @PostMapping
    public ResponseEntity<SpecialistDto> createSpecialist(@RequestBody SpecialistDto specialistDto) {
        SpecialistDto savedSpecialist = specialistService.createSpecialist(specialistDto);
        return new ResponseEntity<>(savedSpecialist, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialistDto> getSpecialistById(@PathVariable Long id) {
        SpecialistDto specialistDto = specialistService.getSpecialistById(id);
        if (specialistDto != null) {
            return new ResponseEntity<>(specialistDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<SpecialistDto>> getAllSpecialisti() {
        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();
        return new ResponseEntity<>(specialisti, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialistDto> updateSpecialist(@PathVariable Long id, @RequestBody SpecialistDto specialistDto) {
        SpecialistDto updatedSpecialist = specialistService.updateSpecialist(id, specialistDto);
        if (updatedSpecialist != null) {
            return new ResponseEntity<>(updatedSpecialist, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialist(@PathVariable Long id) {
        specialistService.deleteSpecialist(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{idUtilizator}/specializari")
    public ResponseEntity<String> addSpecializare(@PathVariable Long idUtilizator, @RequestBody Map<String, Long> requestBody) {
        Long idSpecializare = requestBody.get("idSpecializare");
        if (idSpecializare == null) {
            return new ResponseEntity<>("Corpul cererii trebuie să conțină 'idSpecializare'.", HttpStatus.BAD_REQUEST);
        }
        return specialistService.addSpecializareToSpecialist(idUtilizator, idSpecializare);
    }

    @GetMapping("/{idUtilizator}/specializari")
    public ResponseEntity<List<SpecializareDto>> getSpecializari(@PathVariable Long idUtilizator) {
        return specialistService.getSpecializariForSpecialist(idUtilizator);
    }

    @GetMapping("/{id}/servicii-ids")
    public ResponseEntity<List<Long>> getServiciiIdsForSpecialist(@PathVariable Long id) {
        SpecialistDto s = specialistService.getSpecialistById(id);
        if (s == null) {
            return ResponseEntity.notFound().build();
        }
        // chiar şi dacă s.getServiciuIds() e golă, întoarce tot 200 cu []
        return ResponseEntity.ok(s.getServiciuIds());
    }

    @GetMapping("/lista")
    public ResponseEntity<List<SpecialistListDto>> getAllSpecialistiCuNume() {
        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();
        List<SpecialistListDto> lista = specialisti.stream().map(s -> {
            String nume = utilizatorClient
                    .getPublicUtilizatorById(s.getIdUtilizator())
                    .getNume();
            String desc = Optional.ofNullable(s.getDescriere())
                    .map(d-> d.length()>50? d.substring(0,50)+"…" : d)
                    .orElse("");
            //  aici apelăm serviciul de servicii:
            List<ServiciuDto> srv = serviciiClient.getServiciiBySpecialist(s.getId());
            return new SpecialistListDto(
                    s.getId(), s.getIdUtilizator(), nume, s.getAtestat(), desc, srv
            );
        }).toList();
        return ResponseEntity.ok(lista);
    }

//    @GetMapping("/lista")
//    public ResponseEntity<List<SpecialistListDto>> getAllSpecialistiCuNume() {
//        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();
//
//
//        List<SpecialistListDto> lista = specialisti.stream()
//                .map(s -> {
//                    // 1) Nume+descriere
//                    UtilizatorPublicDto u = utilizatorClient.getPublicUtilizatorById(s.getIdUtilizator());
//                    String nume = Optional.ofNullable(u.getNume()).orElse("—");
//                    String desc = Optional.ofNullable(s.getDescriere())
//                            .map(d-> d.length()>50? d.substring(0,50)+"…" : d)
//                            .orElse("");
//
//                    // 2) lista de servicii full DTO
//                    List<ServiciuDto> srv =
//                            serviciiClient.getServiciiBySpecialist(s.getId());
//
//                    // 3) populează SpecialistListDto
//                    return new SpecialistListDto(
//                            s.getId(),
//                            s.getIdUtilizator(),
//                            nume,
//                            s.getAtestat(),
//                            desc,
//                            srv     // ← aici trece lista de servicii
//                    );
//                })
//                .toList();
//
//        return ResponseEntity.ok(lista);
//    }
}