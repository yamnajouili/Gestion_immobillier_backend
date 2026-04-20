package com.example.gestionimmobilier.Controller;


import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Dtos.ProprietaireDto;
import com.example.gestionimmobilier.Service.IClientService;
import com.example.gestionimmobilier.Service.IProprietaireService;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Proprietaire", description = "CRUD Proprietaire")

@RequestMapping("/proprietaires")
@AllArgsConstructor
public class ProprietaireController {
    private IProprietaireService iProprietaireService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody ProprietaireDto request) {
        return ResponseEntity.ok(iProprietaireService.register(request));
    }
}
