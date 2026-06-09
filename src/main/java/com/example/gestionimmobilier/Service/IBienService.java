package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Entity.Bien;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IBienService {



    @Transactional
    Bien createBien(BienDto bienDto);

    Bien createBienWithImages(String bienJson, MultipartFile[] files);
    List<BienDto> getAllBiens();
    List<BienDto> getMyBiens();
    BienDto getBienById(Long id);




    @Transactional
    Bien updateBien(BienDto bienDto);

    @Transactional
    void deleteBien(Long id);





}
