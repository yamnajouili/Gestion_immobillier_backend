package com.example.gestionimmobilier.Dtos;

import lombok.Data;

import java.util.List;

@Data
public class RecommandationIaRequest {

    private PreferenceUtilisateurDto preference;
    private List<BienIaDto> biens;
}