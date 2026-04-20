package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Entity.ImageFile;
import com.example.gestionimmobilier.Enum.TypeBien;
import lombok.Data;

import java.util.List;

@Data
public class BienDto {

    private  Long id;
    private String titre;
    private String description;
    private Double prix;
    private Double surface;
    private TypeBien type;
    private String adresse;
    private String ville;
    private Boolean disponible;
    private List<ImageFileDto> images;

}
