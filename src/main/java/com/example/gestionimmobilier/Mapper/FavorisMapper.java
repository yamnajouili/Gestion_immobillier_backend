package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.FavorisDto;

import com.example.gestionimmobilier.Entity.Favoris;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FavorisMapper implements IFavorisMapper{

    private final ModelMapper modelMapper;
    @Override
    public FavorisDto fromFavoris(Favoris favoris) {


        return modelMapper.map(favoris, FavorisDto.class);
    }

    @Override
    public Favoris fromFavorisDTO(FavorisDto favorisDto) {
        return modelMapper.map(favorisDto, Favoris.class);
    }
}
