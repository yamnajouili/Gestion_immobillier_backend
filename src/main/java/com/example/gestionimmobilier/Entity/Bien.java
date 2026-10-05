package com.example.gestionimmobilier.Entity;

import com.example.gestionimmobilier.Enum.TypeBien;
import com.example.gestionimmobilier.Enum.TypeTransaction;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Entity
@Table(name = "bien")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder


public class Bien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String titre;
    private String description;
    private Double prix;
    private Double surface;
    private TypeBien type;
    private String adresse;
    private String ville;
    private Double latitude;
    private Double longitude;
    private Boolean disponible;
    private TypeTransaction typeTransaction;
    @ManyToOne
    @JoinColumn(name = "proprietaire_id")
    private Proprietaire proprietaire;
    @OneToMany(mappedBy = "bien", cascade = CascadeType.ALL)
    private List<ImageFile> images;
}
