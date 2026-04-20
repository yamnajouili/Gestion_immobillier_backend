package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Dtos.RecommandationDto;
import com.example.gestionimmobilier.Entity.Contrat;
import com.example.gestionimmobilier.Entity.Recommandation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IRecommandationService {

    @Transactional
    Recommandation create(RecommandationDto recommandationDto);


    List<RecommandationDto> getAllRecommandations();

    RecommandationDto getRecommandationById(Long id);




    @Transactional
    Recommandation updateRecommandation(RecommandationDto recommandationDto);

    @Transactional
    void deleteRecommandation(Long id);
}
