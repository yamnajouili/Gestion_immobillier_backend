package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Contrat;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IContratService {

    @Transactional
    Contrat createContrat(ContratDto contratDto);


    List<ContratDto> getAllContrats();

    ContratDto getContratById(Long id);




    @Transactional
    Contrat updateContrat(ContratDto contratDto);

    @Transactional
    void deleteContrat(Long id);
}
