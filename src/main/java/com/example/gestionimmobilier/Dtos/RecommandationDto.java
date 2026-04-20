package com.example.gestionimmobilier.Dtos;


import lombok.Data;

import java.util.Date;
@Data
public class RecommandationDto {

    private  Long id;
    private Double score;
    private String algorithme;
    private Date dateGeneration;
    private String motifs;
    private BienDto bien;
    private PreferenceUtilisateurDto preferenceUtilisateur;
}
