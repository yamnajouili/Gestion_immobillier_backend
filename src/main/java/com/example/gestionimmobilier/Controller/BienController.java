package com.example.gestionimmobilier.Controller;


import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.ImageFile;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ImageFileRepository;
import com.example.gestionimmobilier.Service.IBienService;
import com.example.gestionimmobilier.storage.StorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@Tag(name = "Biens", description = "CRUD Biens")

@RequestMapping("/biens")
@AllArgsConstructor
public class BienController {



    private final IBienService iBienService;

    @PostMapping(value = "/add-with-images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Bien> createBienWithImages(
            @RequestPart("bien") String bienJson,
            @RequestPart(value = "files", required = false) MultipartFile[] files) {

        Bien saved = iBienService.createBienWithImages(bienJson, files);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel bien")
    public Bien createBien(@RequestBody BienDto request) {
        return iBienService.createBien(request);
    }

    @GetMapping("/read")
    @Operation(summary = "Lister tous les Biens")
    public List<BienDto> getAllBiens() {
        return iBienService.getAllBiens();
    }
    @GetMapping("/my-biens")
    public List<BienDto> getMyBiens() {
        return iBienService.getMyBiens();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un bien par son ID")
    public ResponseEntity<BienDto> getBienById(@PathVariable Long id) {
        return ResponseEntity.ok(iBienService.getBienById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Mettre à jour un bien")
    public ResponseEntity<Bien> updateBien(@RequestBody BienDto bienDto) {
        return ResponseEntity.ok(iBienService.updateBien(bienDto));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un bien")
    public void deleteBien(@PathVariable Long id) {
        iBienService.deleteBien(id);
    }



}
