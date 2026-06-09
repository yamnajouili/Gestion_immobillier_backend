package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratRepository extends JpaRepository<Contrat,Long> {
    Optional<Contrat> findByTokenSignature(String token);

    // Pour lister les contrats d'un propriétaire
    List<Contrat> findByProprietaireId(Long proprietaireId);

    // Pour lister les contrats d'un client
    List<Contrat> findByClientId(Long clientId);
}
