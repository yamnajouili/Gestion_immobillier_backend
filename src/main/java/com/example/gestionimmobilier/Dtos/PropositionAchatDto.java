package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Enum.StatutProposition;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class PropositionAchatDto {

    private Long id;
    private Double montantPropose;
    private LocalDateTime dateProposition;
    private StatutProposition statut;
    private Long clientId;
    private Long bienId;
}
