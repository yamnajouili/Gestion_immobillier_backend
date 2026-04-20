package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Favoris;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavorisRepository extends JpaRepository<Favoris,Long> {



    List<Favoris> findByClientId(Long clientId);

    // ✅ Méthode avec @Query
    @Query("SELECT f FROM Favoris f WHERE f.client.id = :clientId AND f.bien.id = :bienId")
    Optional<Favoris> findByClientIdAndBienId(@Param("clientId") Long clientId, @Param("bienId") Long bienId);



}
