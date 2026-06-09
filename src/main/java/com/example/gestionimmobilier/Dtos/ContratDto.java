package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Enum.StatutContrat;
import lombok.Data;

import java.util.Date;

@Data
public class ContratDto {
    private Long id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private double montantLoyer;
    private double caution;
    private StatutContrat statut;
    private String tokenSignature;
    private BienDto bien;
    private Long bienId;
    private String clientEmail;
    private Long proprietaireId;
}