package com.example.gestionimmobilier.Entity;

import com.example.gestionimmobilier.Enum.StatutProposition;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Entity
@Table(name = "propositonachat")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PropositionAchat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montantPropose;

    private LocalDateTime dateProposition;

    private StatutProposition statut;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Bien bien;
}
