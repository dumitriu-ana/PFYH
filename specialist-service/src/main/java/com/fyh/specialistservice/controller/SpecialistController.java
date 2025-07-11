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
    @GetMapping("/utilizator/{idUtilizator}")
    public ResponseEntity<SpecialistDto> getSpecialistByUtilizatorId(@PathVariable Long idUtilizator) {
        SpecialistDto specialistDto = specialistService.getSpecialistByUtilizatorId(idUtilizator);
        return ResponseEntity.ok(specialistDto);
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
        return ResponseEntity.ok(s.getServiciuIds());
    }

    @GetMapping("/lista")
    public ResponseEntity<List<SpecialistListDto>> getAllSpecialistiCuNume(
            @RequestParam(required = false) Long specializareId
    ) {
        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();

        if (specializareId != null) {
            specialisti = specialisti.stream()
                    .filter(s -> specializareId.equals(s.getSpecializareId()))
                    .collect(Collectors.toList());
        }

        List<SpecialistListDto> lista = specialisti.stream()
                .map(s -> {
                    UtilizatorPublicDto u = utilizatorClient
                            .getPublicUtilizatorById(s.getIdUtilizator());
                    String nume = Optional.ofNullable(u.getNume()).orElse("–");
                    String desc = Optional.ofNullable(s.getDescriere()).orElse("");
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
        List<SpecialistDto> specialisti = specialistService.getAllSpecialisti();

        List<SpecialistListDto> completa = specialisti.stream()
                .map(s -> {
                    UtilizatorPublicDto u = utilizatorClient.getPublicUtilizatorById(s.getIdUtilizator());
                    String nume = Optional.ofNullable(u.getNume()).orElse("–");
                    String desc = Optional.ofNullable(s.getDescriere()).orElse("");

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

        List<SpecialistListDto> filtered = completa.stream()
                .filter(sp -> sp.getServicii().stream()
                        .anyMatch(srv -> srv.getId().equals(serviciuId))
                )
                .collect(Collectors.toList());

        return ResponseEntity.ok(filtered);
    }


    @GetMapping("/admin")
    public ResponseEntity<List<SpecialistAdminDto>> getAllForAdmin() {
        List<SpecialistDto> raw = specialistService.getAllSpecialisti();
        List<SpecialistAdminDto> full = raw.stream().map(s -> {
            UtilizatorPublicDto u = utilizatorClient.getPublicUtilizatorById(s.getIdUtilizator());
            SpecializareDto spz = specializareClient.getSpecializareById(s.getSpecializareId());
            List<ServiciuDto> srv = serviciiClient.getServiciiBySpecialist(s.getId());
            return new SpecialistAdminDto(
                    s.getId(), s.getIdUtilizator(),
                    u.getNume(), u.getEmail(), u.getTipUtilizator(),
                    s.getSpecializareId(), spz.getDenumire(),
                    srv,
                    s.getAtestat(), s.getDescriere(), s.getStatusValidare(),
                    s.getSoldAcumulat(), s.getIdAdmin(),
                    s.getDataValidare()
            );
        }).toList();

        return ResponseEntity.ok(full);
    }

    @PostMapping("/{id}/validare")
    public ResponseEntity<SpecialistDto> validate(
            @PathVariable Long id,
            @RequestBody Map<String, Long> body) {
        Long adminId = body.get("idAdmin");
        SpecialistDto updated = specialistService.validateSpecialist(id, adminId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/validare/{adminId}")
    public ResponseEntity<SpecialistDto> validateAndPromote(
            @PathVariable Long id,
            @PathVariable Long adminId
    ) {
        SpecialistDto updated = specialistService.validateAndPromote(id, adminId);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{id}/servicii/{serviciuId}")
    public ResponseEntity<SpecialistDto> addServiciu(@PathVariable Long id, @PathVariable Long serviciuId) {
        SpecialistDto updated = specialistService.addServiciuToSpecialist(id, serviciuId);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}/servicii/{serviciuId}")
    public ResponseEntity<SpecialistDto> removeServiciu(
            @PathVariable Long id,
            @PathVariable Long serviciuId) {
        SpecialistDto updated = specialistService.removeServiciuFromSpecialist(id, serviciuId);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/statistici/repartitie-specializari")
    public ResponseEntity<List<SpecializareStatisticiDto>> getStatisticiRepartitie() {
        return ResponseEntity.ok(specialistService.getStatisticiSpecializari());
    }
}