package com.example.gestionimmobilier.Dtos;


import lombok.Data;

@Data
public class ImageFileDto {
    private Long id;
    private String url;
    private Boolean estPrincipale;
    private Integer ordre;
}
