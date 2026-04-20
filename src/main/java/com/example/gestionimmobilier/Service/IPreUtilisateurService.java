package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Dtos.PreferenceUtilisateurDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPreUtilisateurService {




    @Transactional
    PreferenceUtilisateur createPreUtilisateur(PreferenceUtilisateurDto preferenceUtilisateurDto);


    List<PreferenceUtilisateurDto> getAllPreUtilisateurs();

    PreferenceUtilisateurDto getPreUtilisateurById(Long id);




    @Transactional
    PreferenceUtilisateur updatePreUtilisateur(PreferenceUtilisateurDto preferenceUtilisateurDto);

    @Transactional
    void deletePreUtilisateur(Long id);






}
