package com.example.gestionimmobilier.Entity;

import com.example.gestionimmobilier.Enum.StatutVisite;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Entity
@Table(name = "visite")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Visite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Date dateProposee;
    private Date dateConfirme;
    private StatutVisite statut;
    private String commentaire;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")  // ← Changez le nom de la colonne
    @JsonBackReference  // ← Empêche la boucle infinie (côté enfant)
    private Client client;


    @ManyToOne
    @JoinColumn(name = "bien_visite_id")
    private Bien bien;
}
