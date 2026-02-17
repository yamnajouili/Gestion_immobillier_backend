package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Proprietaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProprietaireRepository extends JpaRepository<Proprietaire, Long> {
}
