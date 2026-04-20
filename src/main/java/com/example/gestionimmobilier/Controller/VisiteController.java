package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Dtos.VisiteDto;
import com.example.gestionimmobilier.Entity.Visite;
import com.example.gestionimmobilier.Service.IVisiteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Visites", description = "CRUD Visites")

@RequestMapping("/visites")
@AllArgsConstructor
public class VisiteController {

    private final IVisiteService iVisiteService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel Visite")
    public Visite createVisite(@RequestBody VisiteDto request) {
        return iVisiteService.createVisite(request);
    }

    @GetMapping("/read")
    @Operation(summary = "Lister tous les Visites")
    public List<VisiteDto> getAllVisites() {
        return iVisiteService.getAllVisites();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un Visite par son ID")
    public ResponseEntity<VisiteDto> getVisiteById(@PathVariable Long id) {
        return ResponseEntity.ok(iVisiteService.getVisiteById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Mettre à jour un Visite")
    public ResponseEntity<Visite> updateVisite(@RequestBody VisiteDto visiteDto) {
        return ResponseEntity.ok(iVisiteService.updateVisite(visiteDto));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un Visite")
    public void deleteVisite(@PathVariable Long id) {
        iVisiteService.deleteVisite(id);
    }






}
