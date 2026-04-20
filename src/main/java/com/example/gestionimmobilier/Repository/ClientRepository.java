package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByEmail(String email);
}
