package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Dtos.ProprietaireDto;
import com.example.gestionimmobilier.Entity.Proprietaire;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import java.util.List;
import java.util.Optional;


public interface IProprietaireService {




    List<Proprietaire> getAllProprietaires();

    Optional<Proprietaire> getProprietairetById(Long id);

    AuthenticationResponse register(ProprietaireDto request);

    ProprietaireDto updateProfile(ProprietaireDto proprietaireDto);
    void deleteProprietaire(Long id);

    ProprietaireDto getProprietaireById(Long id);
}
