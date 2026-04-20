package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.RecommandationDto;
import com.example.gestionimmobilier.Entity.Recommandation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RecommandationMapper implements IRecommandationMapper{
    private final ModelMapper modelMapper;
    @Override
    public RecommandationDto fromRecommandation(Recommandation recommandation) {
        return modelMapper.map(recommandation, RecommandationDto.class);
    }

    @Override
    public Recommandation fromRecommandationDTO(RecommandationDto recommandationDto) {
        return modelMapper.map(recommandationDto, Recommandation.class);
    }
}
