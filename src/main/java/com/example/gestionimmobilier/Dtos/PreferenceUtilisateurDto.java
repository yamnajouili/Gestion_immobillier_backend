package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Enum.TypeBien;
import lombok.Data;

@Data

public class PreferenceUtilisateurDto {

    private Long id;
    private Double prixMin;
    private Double prixMax;
    private  String villePrefere ;
    private TypeBien typeBienPref;
    private Double surfaceMin ;
}
