package com.example.gestionimmobilier.Dtos;


import com.example.gestionimmobilier.Enum.TypeProprietaire;
import lombok.Data;
import java.util.List;

@Data
public class ProprietaireDto extends UserDto {


    private TypeProprietaire typeProprietaire;
    private String adresseProfessionnelle;
    private String numeroSiret;
    private String nomAgence;
//    private List<ContratDto> contrats;
//    private List<BienDto> biens;

}
