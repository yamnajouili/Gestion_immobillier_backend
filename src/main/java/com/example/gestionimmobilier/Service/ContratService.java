package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Contrat;
import com.example.gestionimmobilier.Mapper.IContratMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ContratRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ContratService implements IContratService {
    private final ContratRepository contratRepository;
    private final IContratMapper iContratMapper;
    private final BienRepository bienRepository;
    @Override
    public Contrat createContrat(ContratDto contratDto) {

        Contrat contrat = iContratMapper.fromContratDTO(contratDto);

        Bien bien = bienRepository.findById(contratDto.getBien().getId())
                .orElseThrow(() -> new RuntimeException("Bien non trouvé"));
        contrat.setBien(bien);

        return contratRepository.save(contrat);
    }
//    @Override
//    public Contrat createContrat(ContratDto contratDto) {
//        Contrat contrat = iContratMapper.fromContratDTO(contratDto);
//
//        return contratRepository.save(contrat);
//    }

    @Override
    public List<ContratDto> getAllContrats() {
        return  contratRepository.findAll().stream().map(
                contrat -> iContratMapper.fromContrat(contrat)
        ).collect(Collectors.toList());
    }

    @Override
    public ContratDto getContratById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("contrat non trouvé"));
        return iContratMapper.fromContrat(contrat);
    }

    @Override
    public Contrat updateContrat(ContratDto contratDto) {
        Contrat contrat = iContratMapper.fromContratDTO(contratDto);
        return contratRepository.save(contrat);
    }

    @Override
    public void deleteContrat(Long id) {

        if (!contratRepository.existsById(id)) {
            throw new RuntimeException("Contrat non trouvé");
        }
        contratRepository.deleteById(id);

    }
}
