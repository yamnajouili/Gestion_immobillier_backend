package com.example.gestionimmobilier.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Entity
@Table(name = "recommandation")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Recommandation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Double score;
    private String algorithme;
    private Date dateGeneration;
    private String motifs;

    @ManyToOne
    @JoinColumn(name = "bien_recommandation_id")
    private Bien bien;

    @ManyToOne
    @JoinColumn(name = "preference_id")
    private PreferenceUtilisateur preferenceUtilisateur;
}
