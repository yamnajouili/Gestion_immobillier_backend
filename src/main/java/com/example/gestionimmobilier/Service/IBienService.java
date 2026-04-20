package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Entity.Bien;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IBienService {



    @Transactional
    Bien createBien(BienDto bienDto);


    List<BienDto> getAllBiens();

    BienDto getBienById(Long id);




    @Transactional
    Bien updateBien(BienDto bienDto);

    @Transactional
    void deleteBien(Long id);





}
