package com.fyh.utilizatorservice.service.impl;


import com.fyh.utilizatorservice.dto.UtilizatorDto;
import com.fyh.utilizatorservice.entity.Utilizator;
import com.fyh.utilizatorservice.mapper.UtilizatorMapper;
import com.fyh.utilizatorservice.repository.UtilizatoriRepository;
import com.fyh.utilizatorservice.service.UtilizatoriService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilizatoriServiceImpl implements UtilizatoriService {
    private final UtilizatoriRepository utilizatoriRepository;

    public UtilizatoriServiceImpl(UtilizatoriRepository utilizatoriRepository) {
        this.utilizatoriRepository = utilizatoriRepository;
    }

    @Override
    public UtilizatorDto createUtilizatori(UtilizatorDto utilizatorDto) {
        Utilizator utilizator = UtilizatorMapper.mapToUtilizatori(utilizatorDto);
        Utilizator savedUtilizator = utilizatoriRepository.save(utilizator);
        return UtilizatorMapper.mapToUtilizatoriDto(savedUtilizator);
    }

    @Override
    public UtilizatorDto getUtilizatoriById(Long id) {
        Utilizator utilizator = utilizatoriRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilizator negasit cu id: " + id));
        return UtilizatorMapper.mapToUtilizatoriDto(utilizator);
    }

    @Override
    public List<UtilizatorDto> getAllUtilizatori() {
        List<Utilizator> utilizator = utilizatoriRepository.findAll();
        return utilizator.stream().map(UtilizatorMapper::mapToUtilizatoriDto).collect(Collectors.toList());
    }

    @Override
    public UtilizatorDto updateUtilizatori(Long id, UtilizatorDto utilizatorDto) {
        Utilizator utilizator = utilizatoriRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilizator negasit cu id: " + id));
        utilizator.setIdFirebase(utilizatorDto.getIdFirebase());
        utilizator.setDataInregistrare(utilizatorDto.getDataInreg());
        utilizator.setNume(utilizatorDto.getNume());
        utilizator.setEmail(utilizatorDto.getEmail());
        utilizator.setParola(utilizatorDto.getParola());
        utilizator.setTipUtilizator(utilizatorDto.getTipUtilizator());
        utilizator.setSold(utilizatorDto.getSold());
        Utilizator updatedUtilizator = utilizatoriRepository.save(utilizator);
        return UtilizatorMapper.mapToUtilizatoriDto(updatedUtilizator);
    }

    @Override
    public void deleteUtilizatori(Long id) {
        utilizatoriRepository.deleteById(id);
    }

    @Override
    public void changeTip(Long id, String nouTip) {
        Utilizator u = utilizatoriRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nu există"));
        u.setTipUtilizator(nouTip);
        utilizatoriRepository.save(u);
    }


    @Transactional
    @Override
    public void adaugaLaSold(Long utilizatorId, BigDecimal sumaDeAdaugat) {
        Utilizator utilizator = utilizatoriRepository.findById(utilizatorId)
                .orElseThrow(() -> new EntityNotFoundException("Utilizator not found with id: " + utilizatorId));
        BigDecimal soldCurent = utilizator.getSold() == null ? BigDecimal.ZERO : utilizator.getSold();
        utilizator.setSold(soldCurent.add(sumaDeAdaugat));
        utilizatoriRepository.save(utilizator);
    }

}
