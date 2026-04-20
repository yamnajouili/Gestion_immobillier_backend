package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.FavorisDto;
import com.example.gestionimmobilier.Entity.Favoris;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IFavorisService {



    Favoris createFavoris( FavorisDto favorisDto);

    List<FavorisDto> getAllFavoris();

    FavorisDto getFavorisById(Long id);

    @Transactional
    Favoris updateFavoris(FavorisDto favorisDto);

    @Transactional
    void deleteFavoris(Long id);

    @Transactional
    void deleteFavorisByBienId(Long bienId);


}
