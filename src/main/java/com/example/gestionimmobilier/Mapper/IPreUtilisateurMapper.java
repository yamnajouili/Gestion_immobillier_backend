package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.PreferenceUtilisateurDto;
import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;

public interface IPreUtilisateurMapper {


    PreferenceUtilisateurDto fromPreUtilisateur(PreferenceUtilisateur preferenceUtilisateur);
    PreferenceUtilisateur fromPreUtilisateurDTO(PreferenceUtilisateurDto preferenceUtilisateurDto);


}
