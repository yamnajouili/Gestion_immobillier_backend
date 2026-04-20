package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Enum.StatutContrat;

import lombok.Data;

import java.util.Date;
@Data
public class ContratDto {
    private  Long id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private double montantLoyer;
    private double caution;
    private StatutContrat statut;
    private BienDto bien;
}
