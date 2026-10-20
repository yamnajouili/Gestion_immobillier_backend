
package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Dtos.PropositionAchatDto;
import com.example.gestionimmobilier.Service.IPropositionAchatService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

@RestController
@RequestMapping("/propositions")
@AllArgsConstructor
public class PropositionAchatController {

    private final IPropositionAchatService iPropositionAchatService;

    // Client : faire une proposition d'achat
    @PostMapping("/add")
    @PreAuthorize("hasRole('CLIENT')")
    public PropositionAchatDto ajouter(
            @RequestBody PropositionAchatDto dto) {

        return iPropositionAchatService.ajouter(dto);
    }

    // Client : consulter ses propositions
    @GetMapping("/client/{clientId}")
    public List<PropositionAchatDto> getByClient(
            @PathVariable Long clientId) {

        return iPropositionAchatService
                .getPropositionsByClient(clientId);
    }

    // Propriétaire : consulter les propositions d'un bien
    @GetMapping("/bien/{bienId}")
    public List<PropositionAchatDto> getByBien(
            @PathVariable Long bienId) {

        return iPropositionAchatService
                .getPropositionsByBien(bienId);
    }

    // Propriétaire : accepter une proposition
    @PutMapping("/accepter/{id}")
    public PropositionAchatDto accepter(
            @PathVariable Long id) {

        return iPropositionAchatService.accepter(id);
    }

    // Propriétaire : refuser une proposition
    @PutMapping("/refuser/{id}")
    public PropositionAchatDto refuser(
            @PathVariable Long id) {

        return iPropositionAchatService.refuser(id);
    }

    @GetMapping("/proprietaire")
    public ResponseEntity<List<PropositionAchatDto>> getPropositionsProprietaire() {
        return ResponseEntity.ok(
                iPropositionAchatService.getPropositionsProprietaire()
        );
}}
