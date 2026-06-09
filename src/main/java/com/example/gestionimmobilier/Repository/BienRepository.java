package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface BienRepository extends JpaRepository<Bien,Long> {
    boolean existsByTitreAndAdresse(String titre, String adresse);

    List<Bien> findByProprietaireEmail(String email);
}
