package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienIaDto;
import com.example.gestionimmobilier.Dtos.PreferenceUtilisateurDto;
import com.example.gestionimmobilier.Dtos.RecommandationIaRequest;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ClientRepository;
import com.example.gestionimmobilier.Mapper.IPreUtilisateurMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
public class IaService {

    private final RestClient restClient;
    private final ClientRepository clientRepository;
    private final BienRepository bienRepository;
    private final IPreUtilisateurMapper iPreUtilisateurMapper;

    public IaService(
            ClientRepository clientRepository,
            BienRepository bienRepository,
            IPreUtilisateurMapper iPreUtilisateurMapper) {

        this.clientRepository = clientRepository;
        this.bienRepository = bienRepository;
        this.iPreUtilisateurMapper = iPreUtilisateurMapper;

        this.restClient = RestClient.builder()
                .baseUrl("http://localhost:8000")
                .build();
    }

    // Appel manuel actuel
    public Object recommander(
            PreferenceUtilisateurDto preference,
            List<BienIaDto> biens) {

        RecommandationIaRequest request = new RecommandationIaRequest();

        request.setPreference(preference);
        request.setBiens(biens);

        return restClient.post()
                .uri("/recommandations")
                .body(request)
                .retrieve()
                .body(Object.class);
    }

    // Nouvelle méthode : recommandations pour le client connecté
    public Object recommanderPourClientConnecte() {

        // 1. Récupérer l'email de l'utilisateur connecté
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // 2. Récupérer le client
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Client introuvable"));

        // 3. Récupérer ses préférences
        PreferenceUtilisateur preference = client.getPreference();

        if (preference == null) {
            throw new RuntimeException(
                    "Aucune préférence enregistrée pour ce client");
        }

        // 4. Transformer la préférence en DTO
        PreferenceUtilisateurDto preferenceDto =
                iPreUtilisateurMapper.fromPreUtilisateur(preference);

        // 5. Récupérer uniquement les biens disponibles
        List<Bien> biens = bienRepository.findByDisponibleTrue();

        // 6. Transformer les biens pour l'IA
        List<BienIaDto> biensIa = biens.stream()
                .map(this::convertirEnBienIaDto)
                .toList();

        // 7. Envoyer les préférences + biens à Python
        return recommander(preferenceDto, biensIa);
    }

    // Conversion Bien → BienIaDto
    private BienIaDto convertirEnBienIaDto(Bien bien) {

        BienIaDto dto = new BienIaDto();

        dto.setId(bien.getId());
        dto.setPrix(bien.getPrix());
        dto.setSurface(bien.getSurface());
        dto.setVille(bien.getVille());
        dto.setTypeBien(bien.getType());

        return dto;
    }
}