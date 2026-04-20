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

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "bien_recommandation_id", referencedColumnName = "id")
    private Bien bien;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "preference_id", referencedColumnName = "id")
    private PreferenceUtilisateur preferenceUtilisateur;
}
