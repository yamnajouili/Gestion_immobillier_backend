package com.example.gestionimmobilier.Entity;

import com.example.gestionimmobilier.Enum.StatutContrat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;


@Entity
@Table(name = "contrat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Contrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private double montantLoyer;
    private double caution;
    private  StatutContrat statut;


    @OneToOne
    @JoinColumn(name = "bien_contrat_id", referencedColumnName = "id")
    private Bien bien;

}
