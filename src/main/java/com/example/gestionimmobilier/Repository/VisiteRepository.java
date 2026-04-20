package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Visite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisiteRepository extends JpaRepository<Visite,Long> {
}
