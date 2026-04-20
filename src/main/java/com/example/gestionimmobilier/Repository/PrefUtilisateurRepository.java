package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrefUtilisateurRepository extends JpaRepository<PreferenceUtilisateur,Long> {
}
