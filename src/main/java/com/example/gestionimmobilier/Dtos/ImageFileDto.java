package com.example.gestionimmobilier.Dtos;


import lombok.Data;

@Data
public class ImageFileDto {
    private String url;
    private Boolean estPrincipale;
    private Integer ordre;
}
