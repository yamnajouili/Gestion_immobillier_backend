package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Dtos.PreferenceUtilisateurDto;
import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;
import com.example.gestionimmobilier.Service.IPreUtilisateurService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Preferences", description = "CRUD Preferences")

@RequestMapping("/preferences")
@AllArgsConstructor
public class PreUtilisateurController {

    private final IPreUtilisateurService iPreUtilisateurService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel preUtilisateur")
    public PreferenceUtilisateur createPreUtilisateur(@RequestBody PreferenceUtilisateurDto request) {
        return iPreUtilisateurService.createPreUtilisateur(request);
    }

    @GetMapping("/read")
    @Operation(summary = "Lister tous les preUtilisateurs")
    public List<PreferenceUtilisateurDto> getAllPreUtilisateur() {
        return iPreUtilisateurService.getAllPreUtilisateurs();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un preUtilisateur par son ID")
    public ResponseEntity<PreferenceUtilisateurDto> getPreUtilisateurById(@PathVariable Long id) {
        return ResponseEntity.ok(iPreUtilisateurService.getPreUtilisateurById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Mettre à jour un contrat")
    public ResponseEntity<PreferenceUtilisateur> updatePreUtilisateur(@RequestBody PreferenceUtilisateurDto preferenceUtilisateurDto) {
        return ResponseEntity.ok(iPreUtilisateurService.updatePreUtilisateur(preferenceUtilisateurDto));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un preUtilisateur")
    public void deletePreUtilisateur(@PathVariable Long id) {
        iPreUtilisateurService.deletePreUtilisateur(id);
    }


}
