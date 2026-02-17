package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
