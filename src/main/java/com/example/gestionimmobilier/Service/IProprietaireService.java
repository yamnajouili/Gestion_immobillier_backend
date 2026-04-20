package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ProprietaireDto;
import com.example.gestionimmobilier.Entity.Proprietaire;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import java.util.List;
import java.util.Optional;


public interface IProprietaireService {




    List<Proprietaire> getAllProprietaires();

    Optional<Proprietaire> getProprietairetById(Long id);

    AuthenticationResponse register(ProprietaireDto request);


    void deleteProprietaire(Long id);
}
