package com.example.gestionimmobilier.Entity;

import com.example.gestionimmobilier.Enum.StatutContrat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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
    private Long id;

    private String titre;
    private Date dateDebut;
    private Date dateFin;
    private double montantLoyer;
    private double caution;
    private StatutContrat statut;

    // Token pour le lien de signature
    private String tokenSignature;
    private LocalDateTime tokenExpiration;

    // Bien concerné
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bien_contrat_id", referencedColumnName = "id")
    private Bien bien;

    // Client qui va signer
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    // Propriétaire qui crée le contrat
    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Proprietaire proprietaire;
}