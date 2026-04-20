package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Enum.StatutVisite;
import lombok.Data;

import java.util.Date;

@Data
public class VisiteDto {

    private  Long id;
    private Date dateProposee;
    private Date dateConfirme;
    private StatutVisite statut;
    private String commentaire;
    private Long bienId;
    private Long clientId;


}
