package com.example.gestionimmobilier.Controller;


import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Dtos.ProprietaireDto;
import com.example.gestionimmobilier.Service.IClientService;
import com.example.gestionimmobilier.Service.IProprietaireService;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Proprietaire", description = "CRUD Proprietaire")

@RequestMapping("/proprietaires")
@AllArgsConstructor
public class ProprietaireController {
    private IProprietaireService iProprietaireService;




    @GetMapping("/all")
    public ResponseEntity<List<ProprietaireDto>> getAllProprietaires() {

        return ResponseEntity.ok(
                iProprietaireService.getAllProprietaires()
        );
    }




    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody ProprietaireDto request) {
        return ResponseEntity.ok(iProprietaireService.register(request));
    }


    @PutMapping("/profile")
    public ResponseEntity<ProprietaireDto> updateProfile(
            @RequestBody ProprietaireDto proprietaireDto
    ) {
        return ResponseEntity.ok(
                iProprietaireService.updateProfile(proprietaireDto)
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ProprietaireDto> getProprietaireById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                iProprietaireService.getProprietaireById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProprietaire(
            @PathVariable Long id) {

        iProprietaireService.deleteProprietaire(id);

        return ResponseEntity.noContent().build();
    }
}
