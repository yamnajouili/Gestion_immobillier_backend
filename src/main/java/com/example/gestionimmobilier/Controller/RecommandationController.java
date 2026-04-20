package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Dtos.RecommandationDto;
import com.example.gestionimmobilier.Entity.Recommandation;
import com.example.gestionimmobilier.Service.IRecommandationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Recommandations", description = "CRUD Recommandations")

@RequestMapping("/recommandations")
@AllArgsConstructor
public class RecommandationController {

    private final IRecommandationService iRecommandationService;

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel Recommandation")
    public Recommandation createRecommandation(@RequestBody RecommandationDto request) {
        return iRecommandationService.create(request);
    }

    @GetMapping("/read")
    @Operation(summary = "Lister tous les Recommandations")
    public List<RecommandationDto> getAllRecommandations() {
        return iRecommandationService.getAllRecommandations();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un Recommandation par son ID")
    public ResponseEntity<RecommandationDto> getRecommandationById(@PathVariable Long id) {
        return ResponseEntity.ok(iRecommandationService.getRecommandationById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Mettre à jour un Recommandation")
    public ResponseEntity<Recommandation> updateRecommandation(@RequestBody RecommandationDto recommandationDto) {
        return ResponseEntity.ok(iRecommandationService.updateRecommandation(recommandationDto));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un Recommandation")
    public void deleteRecommandation(@PathVariable Long id) {
        iRecommandationService.deleteRecommandation(id);
    }



}
