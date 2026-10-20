package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.PropositionAchatDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface IPropositionAchatService {
    // Ajouter une proposition d'achat
    @Transactional
    PropositionAchatDto ajouter(PropositionAchatDto dto);

    // Consulter les propositions d'un client
    List<PropositionAchatDto> getPropositionsByClient(Long clientId);
    List<PropositionAchatDto> getPropositionsProprietaire();


    // Consulter les propositions pour un bien
    List<PropositionAchatDto> getPropositionsByBien(Long bienId);

    // Accepter une proposition
    PropositionAchatDto accepter(Long id);

    // Refuser une proposition
    PropositionAchatDto refuser(Long id);
}
