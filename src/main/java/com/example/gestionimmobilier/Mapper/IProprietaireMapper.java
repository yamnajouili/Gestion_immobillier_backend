package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ProprietaireDto;
import com.example.gestionimmobilier.Entity.Proprietaire;

public interface IProprietaireMapper {
    ProprietaireDto fromProprietaire(Proprietaire proprietaire);
    Proprietaire fromProprietaireDTO(ProprietaireDto proprietaireDto);
}
