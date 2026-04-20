package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.RecommandationDto;
import com.example.gestionimmobilier.Entity.Recommandation;

public interface IRecommandationMapper {




    RecommandationDto fromRecommandation(Recommandation recommandation);
    Recommandation fromRecommandationDTO(RecommandationDto recommandationDto);


}
