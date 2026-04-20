package com.example.gestionimmobilier.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "imagefile")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ImageFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private String ulr;
    private Boolean estPrincipale;
    private Integer ordre;
    @ManyToOne
    @JoinColumn(name = "bien_id")
    private Bien bien;
}
