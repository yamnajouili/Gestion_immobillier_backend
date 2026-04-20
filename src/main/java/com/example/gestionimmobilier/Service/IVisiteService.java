package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.VisiteDto;
import com.example.gestionimmobilier.Entity.Visite;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IVisiteService {


    @Transactional
    Visite createVisite(VisiteDto visiteDto);


    List<VisiteDto> getAllVisites();

    VisiteDto getVisiteById(Long id);




    @Transactional
    Visite updateVisite(VisiteDto visiteDto);

    @Transactional
    void deleteVisite(Long id);

}
