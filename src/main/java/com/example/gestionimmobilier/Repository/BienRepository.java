package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Bien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BienRepository extends JpaRepository<Bien,Long> {
}
