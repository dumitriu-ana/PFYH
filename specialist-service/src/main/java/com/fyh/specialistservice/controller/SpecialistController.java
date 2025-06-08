package com.fyh.specialistservice.controller;


import com.fyh.specialistservice.dto.*;
import com.fyh.specialistservice.service.*;
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
    public ResponseEntity<List<SpecialistListDto>> getAllSpecialistiCuNume(
            @RequestParam(required = false) Long specializareId
    ) {
        // 1) Ia toți specialiștii
        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();

        // 2) Dacă s-a transmis specializareId, filtrează
        if (specializareId != null) {
            specialisti = specialisti.stream()
                    .filter(s -> specializareId.equals(s.getSpecializareId()))
                    .collect(Collectors.toList());
        }

        // 3) Mapează în SpecialistListDto (nume+descriere+servicii deja încărcate)
        List<SpecialistListDto> lista = specialisti.stream()
                .map(s -> {
                    UtilizatorPublicDto u = utilizatorClient
                            .getPublicUtilizatorById(s.getIdUtilizator());
                    String nume = Optional.ofNullable(u.getNume()).orElse("–");
                    String desc = Optional.ofNullable(s.getDescriere())
                            .map(d -> d.length()>50? d.substring(0,50)+"…" : d)
                            .orElse("");
                    // feign client către serviciu-service deja aduce lista de ServiciuDto
                    List<ServiciuDto> srv = serviciiClient
                            .getServiciiBySpecialist(s.getId());
                    return new SpecialistListDto(
                            s.getId(), s.getIdUtilizator(),
                            nume, s.getAtestat(), desc,
                            srv
                    );
                })
                .toList();

        return ResponseEntity.ok(lista);
    }

    @GetMapping("/lista/service/{serviciuId}")
    public ResponseEntity<List<SpecialistListDto>> getSpecialistiByService(
            @PathVariable Long serviciuId
    ) {
        // 1) Preia toți specialiștii scalzi (dto fără nume/servicii)
        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();

        // 2) Mapare SpecialistDto → SpecialistListDto (cu nume + servicii)
        List<SpecialistListDto> completa = specialisti.stream()
                .map(s -> {
                    // preia numele
                    UtilizatorPublicDto u = utilizatorClient.getPublicUtilizatorById(s.getIdUtilizator());
                    String nume = Optional.ofNullable(u.getNume()).orElse("–");
                    // scurtează descrierea
                    String desc = Optional.ofNullable(s.getDescriere())
                            .map(d -> d.length()>50 ? d.substring(0,50)+"…" : d)
                            .orElse("");
                    // lista completă de ServiciuDto (Feign call)
                    List<ServiciuDto> srv = serviciiClient.getServiciiBySpecialist(s.getId());
                    return new SpecialistListDto(
                            s.getId(),
                            s.getIdUtilizator(),
                            nume,
                            s.getAtestat(),
                            desc,
                            srv
                    );
                })
                .collect(Collectors.toList());

        // 3) Filtrează după serviciuId
        List<SpecialistListDto> filtered = completa.stream()
                .filter(sp -> sp.getServicii().stream()
                        .anyMatch(srv -> srv.getId().equals(serviciuId))
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(filtered);
    }

}