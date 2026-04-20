package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.VisiteDto;
import com.example.gestionimmobilier.Entity.Visite;

public interface IVisiteMapper {


    VisiteDto fromVisite(Visite visite);
    Visite fromVisiteDTO(VisiteDto visiteDto);
}
