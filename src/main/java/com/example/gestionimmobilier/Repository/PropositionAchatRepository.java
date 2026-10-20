package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.PropositionAchat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropositionAchatRepository  extends JpaRepository<PropositionAchat, Long> {

    List<PropositionAchat> findByBien_Proprietaire_Id(Long proprietaireId);
    // Propositions envoyées par un client
    List<PropositionAchat> findByClientId(Long clientId);

    // Propositions concernant un bien immobilier
    List<PropositionAchat> findByBienId(Long bienId);
}
