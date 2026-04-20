package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Entity.Contrat;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContratMapper implements IContratMapper{

    private final ModelMapper modelMapper;
    @Override
    public ContratDto fromContrat(Contrat contrat) {
        return modelMapper.map(contrat, ContratDto.class);
    }

    @Override
    public Contrat fromContratDTO(ContratDto contratDto) {
        return modelMapper.map(contratDto, Contrat.class);
    }
}
