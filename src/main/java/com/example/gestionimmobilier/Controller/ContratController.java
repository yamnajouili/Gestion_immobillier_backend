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
import java.util.Map;

@RestController
@Tag(name = "Contrats", description = "CRUD Contrats")

@RequestMapping("/contrats")
@AllArgsConstructor
public class ContratController {



    private final IContratService iContratService;
    // ✅ CREATE + envoi email
    @PostMapping("/add")
    @Operation(summary = "Créer un contrat et envoyer email au client")
    public ResponseEntity<Contrat> createContrat(@RequestBody ContratDto request) throws Exception {
        Contrat contrat = iContratService.createContrat(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(contrat);
    }

    // ✅ READ ALL
    @GetMapping
    @Operation(summary = "Lister tous les contrats")
    public ResponseEntity<List<ContratDto>> getAllContrats() {
        return ResponseEntity.ok(iContratService.getAllContrats());
    }

    // ✅ READ BY ID
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un contrat par ID")
    public ResponseEntity<ContratDto> getContratById(@PathVariable Long id) {
        return ResponseEntity.ok(iContratService.getContratById(id));
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un contrat")
    public ResponseEntity<Contrat> updateContrat(
            @PathVariable Long id,
            @RequestBody ContratDto contratDto) {

        contratDto.setId(id);
        return ResponseEntity.ok(iContratService.updateContrat(contratDto));
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un contrat")
    public ResponseEntity<Void> deleteContrat(@PathVariable Long id) {
        iContratService.deleteContrat(id);
        return ResponseEntity.noContent().build();
    }

    // 🔥 NOUVEAU : récupérer contrat via token (pour front Angular)
    @GetMapping("/token/{id}")
    @Operation(summary = "Récupérer un contrat via token")
    public ResponseEntity<ContratDto> getByToken(@PathVariable String id) {
        return ResponseEntity.ok(iContratService.getContratByToken(id));
    }

    // 🔥 NOUVEAU : signer contrat
    @PostMapping("/sign/{id}")
    @Operation(summary = "Signer un contrat via token")
    public ResponseEntity<?> signerContrat(@PathVariable String id) {
        try {
            iContratService.signerContrat(id);

            // ✔ retourner JSON
            return ResponseEntity.ok().body(Map.of(
                    "message", "Contrat signé avec succès"
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                    "error", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "error", "Erreur serveur"
            ));
        }
    }



}
