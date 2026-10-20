package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Enum.Role;
import com.example.gestionimmobilier.Enum.TypeProprietaire;
import lombok.Data;

import java.util.Date;
@Data
public class UserDetailDto {

    private Long id;
    private String nom;
    private String telephone;
    private String poste;
    private String email;
    private Role role;
    private Date creationDate;
    private String photoProfil;

    private TypeProprietaire typeProprietaire;
    private String adresseProfessionnelle;
    private String numeroSiret;
    private String nomAgence;
}
