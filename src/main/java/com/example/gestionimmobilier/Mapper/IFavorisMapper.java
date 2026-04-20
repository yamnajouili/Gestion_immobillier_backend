package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.FavorisDto;
import com.example.gestionimmobilier.Entity.Favoris;

public interface IFavorisMapper {


    FavorisDto fromFavoris(Favoris favoris);

    Favoris fromFavorisDTO(FavorisDto favorisDto);
}
