package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Enum.TypeBien;
import lombok.Data;

@Data
public class BienIaDto {
    private Long id;
    private Double prix;
    private Double surface;
    private String ville;
    private TypeBien typeBien;
}