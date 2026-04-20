package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Dtos.FavorisDto;
import com.example.gestionimmobilier.Entity.Contrat;
import com.example.gestionimmobilier.Entity.Favoris;
import com.example.gestionimmobilier.Service.IClientService;
import com.example.gestionimmobilier.Service.IContratService;
import com.example.gestionimmobilier.Service.IFavorisService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Favoris", description = "CRUD Favoris")

@RequestMapping("/favoris")
@AllArgsConstructor
public class FavorisController {

    private final IFavorisService iFavorisService;
    private final IClientService iClientService;
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un nouvel Favoris")
    public Favoris createFavoris(@RequestBody FavorisDto request) {
        return iFavorisService.createFavoris(request);
    }

    @GetMapping("/read")
    @Operation(summary = "Lister tous les Favoris")
    public List<FavorisDto> getAllContrats() {
        return iFavorisService.getAllFavoris();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un favoris par son ID")
    public ResponseEntity<FavorisDto> getFavorisById(@PathVariable Long id) {
        return ResponseEntity.ok(iFavorisService.getFavorisById(id));
    }

    @PutMapping("/update")
    @Operation(summary = "Mettre à jour un favoris")
    public ResponseEntity<Favoris> updateFavoris(@RequestBody FavorisDto favorisDto) {
        return ResponseEntity.ok(iFavorisService.updateFavoris(favorisDto));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Supprimer un favoris")
    public void deleteFavoris(@PathVariable Long id) {
        iFavorisService.deleteFavoris(id);
    }
    @DeleteMapping("/delete-by-bien")
    public ResponseEntity<Void> deleteFavorisByBienId(@RequestBody Map<String, Long> request) {
        Long bienId = request.get("bienId");
        iFavorisService.deleteFavorisByBienId(bienId);
        return ResponseEntity.noContent().build();
    }

}
