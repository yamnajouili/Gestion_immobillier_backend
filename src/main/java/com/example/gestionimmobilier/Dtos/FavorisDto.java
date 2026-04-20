package com.example.gestionimmobilier.Dtos;


import lombok.Data;
import java.util.Date;

@Data
public class FavorisDto {
    private  Long id;
    private Date dateAjout;
    private Long bienId;

}
