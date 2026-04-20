package com.example.gestionimmobilier.Entity;

import com.example.gestionimmobilier.Enum.TypeBien;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Table(name = "preferenceutilisateur")
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PreferenceUtilisateur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private Double prixMin;
    private Double prixMax;
    private  String villePrefere ;
    private TypeBien typeBienPref;
    private Double surfaceMin ;


}
