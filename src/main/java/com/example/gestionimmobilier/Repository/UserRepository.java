package com.example.gestionimmobilier.Repository;



import com.example.gestionimmobilier.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndPassword(String email, String password);
    Optional<User> findByTelephone( double tel);
    Optional<User> findByEmail(String email);

//    @Query("SELECT u FROM User u JOIN u.avis a WHERE a.id = :avisId")
//    User findByAvisId(@Param("avisId") Long avisId);
}

