package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Entity.Bien;

public interface IBienMapper {



    BienDto fromBien(Bien bien);

    Bien fromBienDTO(BienDto bienDto);
}
