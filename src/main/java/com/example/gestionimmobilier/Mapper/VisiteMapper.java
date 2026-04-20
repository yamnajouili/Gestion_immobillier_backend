package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.VisiteDto;
import com.example.gestionimmobilier.Entity.Visite;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VisiteMapper implements IVisiteMapper{
    private final ModelMapper modelMapper;

    @Override
    public VisiteDto fromVisite(Visite visite) {

        return modelMapper.map(visite, VisiteDto.class);
    }

    @Override
    public Visite fromVisiteDTO(VisiteDto visiteDto) {
        return modelMapper.map(visiteDto, Visite.class);

    }
}
