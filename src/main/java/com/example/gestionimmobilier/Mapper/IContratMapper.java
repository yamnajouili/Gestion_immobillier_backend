package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Entity.Contrat;

public interface IContratMapper {



    ContratDto fromContrat(Contrat contrat);

    Contrat fromContratDTO(ContratDto contratDto);

}
