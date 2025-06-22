package com.fyh.comandaservice.service.impl;


import com.fyh.comandaservice.dto.ComandaDto;
import com.fyh.comandaservice.dto.ServiciuDto;
import com.fyh.comandaservice.dto.SpecialistDto;
import com.fyh.comandaservice.dto.UtilizatorDto;
import com.fyh.comandaservice.entity.Comanda;
import com.fyh.comandaservice.mapper.ComandaMapper;
import com.fyh.comandaservice.repository.ComandaRepository;
import com.fyh.comandaservice.service.ComandaService;
import com.fyh.comandaservice.service.ServiciuClient;
import com.fyh.comandaservice.service.SpecialistClient;
import com.fyh.comandaservice.service.UtilizatorClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fyh.comandaservice.dto.SoldUpdateDto;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComandaServiceImpl implements ComandaService {

    private final ComandaRepository comandaRepository;

    private final UtilizatorClient utilizatorClient;
    private final SpecialistClient specialistClient;
    private final ServiciuClient serviciuClient;
    public ComandaServiceImpl( ComandaRepository comandaRepository, UtilizatorClient utilizatorClient, SpecialistClient specialistClient, ServiciuClient serviciuClient) {
        this.comandaRepository = comandaRepository;
        this.utilizatorClient = utilizatorClient;
        this.specialistClient = specialistClient;
        this.serviciuClient = serviciuClient;
    }

    public ComandaDto getComandaWithDetails(Long idComanda) {
        Comanda comanda = comandaRepository.findById(idComanda).orElse(null);
        if (comanda != null) {
            UtilizatorDto client = utilizatorClient.getUtilizatoriById(comanda.getIdClient());
            SpecialistDto specialist = specialistClient.getSpecialistByUtilizatorId(comanda.getIdSpecialist()); // Sau getSpecialistById
            ServiciuDto serviciu = serviciuClient.getServiciuById(comanda.getIdServiciu());

            ComandaDto comandaDto = ComandaMapper.mapToComandaDto(comanda);
            comandaDto.setIdClient(client.getId());
            comandaDto.setIdSpecialist(specialist.getId());
            comandaDto.setId(serviciu.getId());

            return comandaDto;
        }
        return null;
    }

    @Override
    public ComandaDto createComanda(ComandaDto comandaDto) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        comandaDto.setDataCreare(now);
        Timestamp dataMaximaLivrare = new Timestamp(now.getTime() + 5L * 24 * 60 * 60 * 1000);
        comandaDto.setDataMaximaLivrare(dataMaximaLivrare);
        if (comandaDto.getStatus() == null) {
            comandaDto.setStatus("Plasata");
        }
        Comanda comanda = ComandaMapper.mapToComanda(comandaDto);
        Comanda saved = comandaRepository.save(comanda);

        return ComandaMapper.mapToComandaDto(saved);
    }


    @Override
    public ComandaDto getComandaById(Long id) {
        Comanda comanda = comandaRepository.findById(id).orElse(null);
        return ComandaMapper.mapToComandaDto(comanda);
    }

    @Override
    public List<ComandaDto> getAllComenzi() {
        List<Comanda> comenzi = comandaRepository.findAll();
        return comenzi.stream()
                .map(ComandaMapper::mapToComandaDto)
                .collect(Collectors.toList());
    }

    @Override
    public ComandaDto updateComanda(Long id, ComandaDto comandaDto) {
        Comanda existingComanda = comandaRepository.findById(id).orElse(null);
        if (existingComanda != null) {
            comandaDto.setId(id);
            Comanda updatedComanda = comandaRepository.save(ComandaMapper.mapToComanda(comandaDto));
            return ComandaMapper.mapToComandaDto(updatedComanda);
        }
        return null;
    }

    @Override
    public void deleteComanda(Long id) {
        comandaRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<ComandaDto> getComenziByClientId(Long clientId) {
        try {
            utilizatorClient.getUtilizatoriById(clientId);
        } catch (Exception e) {
            System.err.println("Clientul cu ID " + clientId + " nu a fost gasit.");
            throw new RuntimeException("Client not found", e);
        }

        List<Comanda> comenzi = comandaRepository.findByIdClient(clientId);

        return comenzi.stream()
                .map(comanda -> {
                    ComandaDto dto = ComandaMapper.mapToComandaDto(comanda);
                    try {
                        ServiciuDto serviciu = serviciuClient.getServiciuById(comanda.getIdServiciu());
                        dto.setTitluServiciu(serviciu.getTitlu());
                    } catch (Exception e) {
                        System.err.println("Eroare la obtinerea serviciului cu ID: " + comanda.getIdServiciu());
                        dto.setTitluServiciu("Serviciu indisponibil");
                    }
                    try {
                        SpecialistDto specialistInfo = specialistClient.getSpecialistById(comanda.getIdSpecialist());
                        UtilizatorDto utilizatorDtoSpecialist = utilizatorClient.getUtilizatoriById(specialistInfo.getIdUtilizator());
                        dto.setNumeSpecialist(utilizatorDtoSpecialist.getNume());
                    } catch (Exception e) {
                        System.err.println("Eroare la obtinerea specialistului cu ID: " + comanda.getIdSpecialist());
                        dto.setNumeSpecialist("Specialist indisponibil");
                    }

                    return dto;
                })
                .collect(Collectors.toList());
    }


    //com pt specialist
    @Transactional(readOnly = true)
    @Override
    public List<ComandaDto> getComenziBySpecialistId(Long utilizatorId) {
        // Pas 1: Traducem ID-ul de utilizator in ID-ul de specialist (din tabela Specialist)
        SpecialistDto specialist;
        try {
            specialist = specialistClient.getSpecialistByUtilizatorId(utilizatorId);
            if (specialist == null) {
                throw new RuntimeException("Nu a fost găsit un profil de specialist pentru utilizatorul cu ID: " + utilizatorId);
            }
        } catch (Exception e) {
            throw new RuntimeException("Eroare la comunicarea cu serviciul de specialiști.", e);
        }

        // Pas 2: Folosim ID-ul real de specialist pentru a prelua comenzile
        List<Comanda> comenzi = comandaRepository.findByIdSpecialist(specialist.getId());

        // Pas 3: Procesam fiecare comanda pentru a o imbogati cu date
        return comenzi.stream()
                .map(this::mapComandaToDtoWithDetails) // Folosim aceeasi metoda helper
                .collect(Collectors.toList());
    }

    private ComandaDto mapComandaToDtoWithDetails(Comanda comanda) {
        ComandaDto dto = ComandaMapper.mapToComandaDto(comanda);
        try {
            ServiciuDto serviciu = serviciuClient.getServiciuById(comanda.getIdServiciu());
            dto.setTitluServiciu(serviciu.getTitlu());
        } catch (Exception e) {
            System.err.println("Eroare la preluarea serviciului cu ID: " + comanda.getIdServiciu());
            dto.setTitluServiciu("Serviciu Indisponibil");
        }

        // Incarcam numele clientului
        try {
            UtilizatorDto clientInfo = utilizatorClient.getUtilizatoriById(comanda.getIdClient());
            dto.setNumeClient(clientInfo.getNume());
        } catch (Exception e) {
            System.err.println("Eroare la preluarea clientului cu ID: " + comanda.getIdClient());
            dto.setNumeClient("Client Indisponibil");
        }

        try {
            SpecialistDto specialistInfo = specialistClient.getSpecialistById(comanda.getIdSpecialist());
            UtilizatorDto specialistUserInfo = utilizatorClient.getUtilizatoriById(specialistInfo.getIdUtilizator());
            dto.setNumeSpecialist(specialistUserInfo.getNume());
        } catch (Exception e) {
            System.err.println("Eroare la preluarea numelui specialistului pentru comanda ID: " + comanda.getId());
            dto.setNumeSpecialist("Specialist Indisponibil");
        }
        return dto;
    }


    @Transactional
    @Override
    public ComandaDto raspundeLaComanda(Long id, String mesajSpecialist, MultipartFile fisier) throws IOException {
        Comanda comanda = comandaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comanda not found with id: " + id));
        comanda.setMesajSpecialist(mesajSpecialist);
        if (fisier != null && !fisier.isEmpty()) {
            comanda.setNumeFisierSpecialist(fisier.getOriginalFilename());
            comanda.setFisierSpecialist(fisier.getBytes());
        }
        comanda.setStatus("Finalizata");
        BigDecimal pretTotal = comanda.getPret();
        BigDecimal procentCastig = new BigDecimal("0.90");

        BigDecimal castigSpecialist = pretTotal.multiply(procentCastig).setScale(2, RoundingMode.HALF_UP);
        BigDecimal castigAdmin = pretTotal.subtract(castigSpecialist);

        SpecialistDto specialistInfo = specialistClient.getSpecialistById(comanda.getIdSpecialist());
        Long idUtilizatorSpecialist = specialistInfo.getIdUtilizator();
        Long idAdmin = specialistInfo.getIdAdmin();

        SoldUpdateDto soldUpdate = new SoldUpdateDto();
        soldUpdate.setSumaDeAdaugat(castigSpecialist);

        SoldUpdateDto soldUpdateAdmin = new SoldUpdateDto();
        soldUpdateAdmin.setSumaDeAdaugat(castigAdmin);

        utilizatorClient.adaugaLaSold(idUtilizatorSpecialist, soldUpdate);
        utilizatorClient.adaugaLaSold(idAdmin, soldUpdateAdmin);

        Comanda comandaSalvata = comandaRepository.save(comanda);

        return ComandaMapper.mapToComandaDto(comandaSalvata);
    }


}