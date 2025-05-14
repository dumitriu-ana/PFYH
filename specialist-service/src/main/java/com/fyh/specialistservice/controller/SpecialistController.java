package com.fyh.specialistservice.controller;


import com.fyh.specialistservice.dto.*;
import com.fyh.specialistservice.service.SpecialistService;
import com.fyh.specialistservice.service.SpecialistiSpecializariClient;
import com.fyh.specialistservice.service.SpecializareClient;
import com.fyh.specialistservice.service.UtilizatorClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/specialisti")
@CrossOrigin(origins = "http://localhost:4200") //acces din front
public class SpecialistController {

    private final SpecialistService specialistService;
    private final SpecialistiSpecializariClient specialistiSpecializariClient;
    private final SpecializareClient specializareClient;

    private final UtilizatorClient utilizatorClient;
    public SpecialistController(SpecialistService specialistService,
                                SpecialistiSpecializariClient specialistiSpecializariClient,
                                SpecializareClient specializareClient, UtilizatorClient utilizatorClient) {
        this.specialistService = specialistService;
        this.specialistiSpecializariClient = specialistiSpecializariClient;
        this.specializareClient = specializareClient;
        this.utilizatorClient = utilizatorClient;
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

    @GetMapping("/lista")
    public ResponseEntity<List<SpecialistListDto>> getAllSpecialistiCuNume() {
        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();
        List<SpecialistListDto> lista = specialisti.stream()
                .map(s -> {
                    // apelezi doar campurile publice
                    UtilizatorPublicDto u = utilizatorClient.getPublicUtilizatorById(s.getIdUtilizator());
                    String nume = u.getNume();
                    String desc = Optional.ofNullable(s.getDescriere())
                            .map(d -> d.length()>50? d.substring(0,50)+"…" : d)
                            .orElse("");
                    return new SpecialistListDto(
                            s.getId(), s.getIdUtilizator(),
                            nume, s.getAtestat(), desc
                    );
                })
                .toList();
        return ResponseEntity.ok(lista);
    }

//    @GetMapping("/lista")
//    public ResponseEntity<List<SpecialistListDto>> getAllSpecialistiCuNume() {
//        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();
//        List<SpecialistListDto> specialistiCuNume = specialisti.stream()
//                .map(specialist -> {
//                    UtilizatorDto utilizator = utilizatorClient.getUtilizatoriById(specialist.getIdUtilizator());
//                    String descriere = specialist.getDescriere();
//                    String shortDescriere = "";
//                    if (descriere != null) {
//                        shortDescriere = descriere.length() > 50 ? descriere.substring(0, 50) + "..." : descriere;
//                    }
//
//                    return new SpecialistListDto(
//                            specialist.getId(),
//                            specialist.getIdUtilizator(),
//                            utilizator != null ? utilizator.getNume() : "Nume Indisponibil",
//                            specialist.getAtestat(),
//                            shortDescriere
//                    );
//                })
//                .collect(Collectors.toList());
//        return new ResponseEntity<>(specialistiCuNume, HttpStatus.OK);
//    }
}