package com.example.gestionimmobilier.Repository;

import com.example.gestionimmobilier.Entity.ImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageFileRepository extends JpaRepository<ImageFile,Long>{
    void deleteByUrl(String url);
    Optional<ImageFile> findByUrl(String ulr);

}
