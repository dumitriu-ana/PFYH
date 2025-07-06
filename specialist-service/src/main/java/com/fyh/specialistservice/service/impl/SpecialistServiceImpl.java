package com.fyh.specialistservice.service.impl;

import com.fyh.specialistservice.dto.SpecialistDto;
import com.fyh.specialistservice.dto.SpecializareDto;
import com.fyh.specialistservice.dto.SpecializareStatisticiDto;
import com.fyh.specialistservice.entity.Specialist;
import com.fyh.specialistservice.mapper.SpecialistMapper;
import com.fyh.specialistservice.repository.SpecialistRepository;
import com.fyh.specialistservice.service.SpecialistService;
import com.fyh.specialistservice.service.SpecialistiSpecializariClient;
import com.fyh.specialistservice.service.SpecializareClient;
import com.fyh.specialistservice.service.UtilizatorClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SpecialistServiceImpl implements SpecialistService {

    private final SpecialistRepository specialistRepository;
    private final UtilizatorClient apiClient;
    private final SpecialistiSpecializariClient specialistiSpecializariClient;
    private final SpecializareClient specializareClient;

    public SpecialistServiceImpl(SpecialistRepository specialistRepository,
                                 UtilizatorClient apiClient,
                                 SpecialistiSpecializariClient specialistiSpecializariClient,
                                 SpecializareClient specializareClient) {
        this.specialistRepository = specialistRepository;
        this.apiClient = apiClient;
        this.specialistiSpecializariClient = specialistiSpecializariClient;
        this.specializareClient = specializareClient;
    }

    @Override
    public SpecialistDto createSpecialist(SpecialistDto specialistDto) {
        Long idUtilizator = specialistDto.getIdUtilizator();
        if (idUtilizator != null) {
            try {
                apiClient.getUtilizatoriById(idUtilizator);
            } catch (ResponseStatusException e) {
                if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                    throw new IllegalArgumentException("Utilizatorul cu ID-ul " + idUtilizator + " nu există.");
                } else {
                    throw e;
                }
            }
        }
        Specialist entity = new Specialist();
        entity.setIdUtilizator(  specialistDto.getIdUtilizator()   );
        entity.setSpecializareId(specialistDto.getSpecializareId());
        entity.setServiciuIds(   new HashSet<>(specialistDto.getServiciuIds()) );
        entity.setAtestat(       specialistDto.getAtestat()        );
        entity.setStatusValidare(specialistDto.getStatusValidare());
        entity.setDescriere(     specialistDto.getDescriere()      );
        entity.setSoldAcumulat(  specialistDto.getSoldAcumulat()   );
        entity.setIdAdmin(       specialistDto.getIdAdmin()        );
        entity.setDataValidare(  specialistDto.getDataValidare()   );

        Specialist saved = specialistRepository.save(entity);
        return SpecialistMapper.mapToSpecialistDto(saved);
    }

    @Override
    public SpecialistDto getSpecialistById(Long id) {
        return specialistRepository.findById(id)
                .map(SpecialistMapper::mapToSpecialistDto)
                .orElse(null);
    }

    @Override
    public List<SpecialistDto> getAllSpecialisti() {
        List<Specialist> specialisti = specialistRepository.findAll();
        return specialisti.stream()
                .map(SpecialistMapper::mapToSpecialistDto)
                .collect(Collectors.toList());
    }

    @Override
    public SpecialistDto updateSpecialist(Long id, SpecialistDto specialistDto) {
        Specialist existingSpecialist = specialistRepository.findById(id).orElse(null);
        if (existingSpecialist != null) {
            specialistDto.setId(id);
            Specialist updatedSpecialist = specialistRepository.save(SpecialistMapper.mapToSpecialist(specialistDto));
            return SpecialistMapper.mapToSpecialistDto(updatedSpecialist);
        }
        return null;
    }

    @Override
    public void deleteSpecialist(Long id) {
        specialistRepository.deleteById(id);
    }

    @Override
    public ResponseEntity<String> addSpecializareToSpecialist(Long idUtilizator, Long idSpecializare) {
        Specialist specialist = specialistRepository.findByIdUtilizator(idUtilizator).orElse(null);
        if (specialist == null) {
            return new ResponseEntity<>("Specialistul cu ID-ul utilizator " + idUtilizator + " nu există.", HttpStatus.NOT_FOUND);
        }
        try {
            specializareClient.getSpecializareById(idSpecializare);
            List<SpecializareDto> existingSpecializari = getSpecializariForSpecialist(idUtilizator).getBody();
            if (existingSpecializari != null && existingSpecializari.stream().anyMatch(s -> s.getId().equals(idSpecializare))) {
                return new ResponseEntity<>("Specializarea este deja adăugată pentru acest specialist.", HttpStatus.CONFLICT);
            }

            Map<String, Long> payload = new HashMap<>();
            payload.put("idSpecialist", specialist.getIdUtilizator());
            payload.put("idSpecializare", idSpecializare);

            try {
                ResponseEntity<Void> response = specialistiSpecializariClient.addSpecialistSpecializare(payload);
                if (response.getStatusCode().is2xxSuccessful()) {
                    return new ResponseEntity<>("Specializare adăugată cu succes.", HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Eroare la adăugarea specializării.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (RestClientException e) {
                return new ResponseEntity<>("Eroare la comunicarea cu serviciul de asocieri.", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return new ResponseEntity<>("Specializarea cu ID-ul " + idSpecializare + " nu există.", HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>("Eroare la verificarea specializării.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (RestClientException e) {
            return new ResponseEntity<>("Eroare la comunicarea cu serviciul de specializări.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<List<com.fyh.specialistservice.dto.SpecializareDto>> getSpecializariForSpecialist(Long idUtilizator) {
        List<Long> specializariIds = specialistiSpecializariClient.getSpecializariForSpecialist(idUtilizator);
        if (specializariIds == null || specializariIds.isEmpty()) {
            return new ResponseEntity<>(List.of(), HttpStatus.OK);
        }

        List<com.fyh.specialistservice.dto.SpecializareDto> specializari = specializariIds.stream()
                .map(specializareClient::getSpecializareById)
                .collect(Collectors.toList());

        return new ResponseEntity<>(specializari, HttpStatus.OK);
    }

    @Override
    public SpecialistDto validateSpecialist(Long id, Long adminId) {
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialistul nu există"));
        specialist.setStatusValidare("VALIDAT");
        specialist.setIdAdmin(adminId);
        specialist.setDataValidare(Timestamp.valueOf(LocalDateTime.now()));
        Specialist saved = specialistRepository.save(specialist);
        return SpecialistMapper.mapToSpecialistDto(saved);
    }

    //cand se valideaza un specialist. client ---> specialist
    @Override
    public SpecialistDto validateAndPromote(Long id, Long adminId) {
        SpecialistDto dto = getSpecialistById(id);
        if (dto == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        dto.setStatusValidare("VALIDAT");
        dto.setIdAdmin(adminId);
        dto.setDataValidare(new Timestamp(System.currentTimeMillis()));
        dto.setServiciuIds(Collections.emptyList());
        SpecialistDto saved = updateSpecialist(id, dto);
        apiClient.changeTipUtilizator(dto.getIdUtilizator(), Map.of("tipUtilizator","SPECIALIST"));
        return saved;
    }

    @Override
    public SpecialistDto addServiciuToSpecialist(Long specialistId, Long serviciuId) {
        Specialist ent = specialistRepository.findById(specialistId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialist inexistent"));
        // Ad dacă nu e deja
        if (!ent.getServiciuIds().contains(serviciuId)) {
            ent.getServiciuIds().add(serviciuId);
            specialistRepository.save(ent);
        }
        return SpecialistMapper.mapToSpecialistDto(ent);
    }

    //elim serviciu
    @Override
    public SpecialistDto removeServiciuFromSpecialist(Long id, Long serviciuId) {
        Specialist specialist = specialistRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Specialistul nu există"));
        if (specialist.getServiciuIds().remove(serviciuId)) {
            specialist = specialistRepository.save(specialist);
        }
        return SpecialistMapper.mapToSpecialistDto(specialist);
    }

    @Override
    public List<SpecializareStatisticiDto> getStatisticiSpecializari() {
        List<Specialist> totiSpecialistii = specialistRepository.findAll();

        Map<Long, Long> numarSpecialistiPerSpecializare = totiSpecialistii.stream()
                .filter(s -> s.getSpecializareId() != null)
                .collect(Collectors.groupingBy(Specialist::getSpecializareId, Collectors.counting()));

        List<SpecializareStatisticiDto> statistici = new ArrayList<>();
        numarSpecialistiPerSpecializare.forEach((idSpecializare, numarSpecialisti) -> {
            try {
                SpecializareDto specializare = specializareClient.getSpecializareById(idSpecializare);
                if (specializare != null && specializare.getDenumire() != null) {
                    statistici.add(new SpecializareStatisticiDto(specializare.getDenumire(), numarSpecialisti));
                }
            } catch (Exception e) {
                System.err.println("Eroare la specializarea cu ID: " + idSpecializare);
            }
        });

        return statistici;
    }

    @Override
    public SpecialistDto getSpecialistByUtilizatorId(Long idUtilizator) {
        Specialist specialist = specialistRepository.findByIdUtilizator(idUtilizator)
                .orElseThrow(() -> new RuntimeException("Specialist not found for utilizator id: " + idUtilizator));
        return SpecialistMapper.mapToSpecialistDto(specialist);
    }
}