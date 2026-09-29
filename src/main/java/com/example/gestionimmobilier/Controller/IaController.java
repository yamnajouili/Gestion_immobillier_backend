package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Dtos.RecommandationIaRequest;
import com.example.gestionimmobilier.Service.IaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ia")
@RequiredArgsConstructor
@Tag(name = "IA", description = "Gestion des recommandations intelligentes")
public class IaController {



    private final IaService iaService;

    @GetMapping("/recommandations")
    public Object recommanderPourClientConnecte() {

        return iaService.recommanderPourClientConnecte();
    }



}
