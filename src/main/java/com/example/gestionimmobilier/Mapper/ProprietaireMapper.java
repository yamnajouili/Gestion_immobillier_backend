package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ProprietaireDto;
import com.example.gestionimmobilier.Entity.Proprietaire;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProprietaireMapper implements IProprietaireMapper {
    private final ModelMapper modelMapper;

    @Override
    public ProprietaireDto fromProprietaire(Proprietaire proprietaire) {
        return modelMapper.map(proprietaire, ProprietaireDto.class);
    }

    @Override
    public Proprietaire fromProprietaireDTO(ProprietaireDto proprietaireDto) {
        return modelMapper.map(proprietaireDto, Proprietaire.class);
    }
}
