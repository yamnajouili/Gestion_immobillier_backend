package com.example.gestionimmobilier.Entity;

import com.example.gestionimmobilier.Enum.TypeProprietaire;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity

@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "proprietaire")
public class Proprietaire extends User{
    private TypeProprietaire typeProprietaire;
    private String adresseProfessionnelle;
    private String numeroSiret;
    private String nomAgence;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proprietaire_contrat" )
    private List<Contrat> contrats;
    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proprietaire_bien" )
    private List<Bien> biens;

}
