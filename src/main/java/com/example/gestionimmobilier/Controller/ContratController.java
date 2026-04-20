package com.example.gestionimmobilier.Controller;


import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Entity.Contrat;
import com.example.gestionimmobilier.Service.IContratService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Contrats", description = "CRUD Contrats")

@RequestMapping("/contrats")
@AllArgsConstructor
public class ContratController {



    private final IContratService iContratService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel Contrat")
    public Contrat createContrat(@RequestBody ContratDto request) {
        return iContratService.createContrat(request);
    }

    @GetMapping("/read")
    @Operation(summary = "Lister tous les Contrats")
    public List<ContratDto> getAllContrats() {
        return iContratService.getAllContrats();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un contrat par son ID")
    public ResponseEntity<ContratDto> getContratById(@PathVariable Long id) {
        return ResponseEntity.ok(iContratService.getContratById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Mettre à jour un contrat")
    public ResponseEntity<Contrat> updateContrat(@RequestBody ContratDto contratDto) {
        return ResponseEntity.ok(iContratService.updateContrat(contratDto));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un contrat")
    public void deleteContrat(@PathVariable Long id) {
        iContratService.deleteContrat(id);
    }





}
