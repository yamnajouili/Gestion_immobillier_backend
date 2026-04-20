package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Contrat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratRepository extends JpaRepository<Contrat,Long> {
}
