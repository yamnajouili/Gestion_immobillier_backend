package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile,Long>{
    void deleteByUlr(String ulr);
    Optional<ImageFile> findByUlr(String ulr);

}
