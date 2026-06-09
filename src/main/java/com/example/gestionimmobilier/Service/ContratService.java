package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ContratDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.Contrat;
import com.example.gestionimmobilier.Entity.Proprietaire;
import com.example.gestionimmobilier.Enum.StatutContrat;
import com.example.gestionimmobilier.Mapper.IContratMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ClientRepository;
import com.example.gestionimmobilier.Repository.ContratRepository;
import com.example.gestionimmobilier.Repository.ProprietaireRepository;
import com.example.gestionimmobilier.email.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ContratService implements IContratService {

    private final ContratRepository contratRepository;
    private final IContratMapper iContratMapper;
    private final BienRepository bienRepository;
    private final ClientRepository clientRepository;
    private final ProprietaireRepository proprietaireRepository;
    private final EmailService emailService;

    @Override
    public Contrat createContrat(ContratDto contratDto) {

        // 1. Récupérer le bien
        Bien bien = bienRepository.findById(contratDto.getBienId())
                .orElseThrow(() -> new RuntimeException("Bien non trouvé avec id: " + contratDto.getBienId()));

        // 2. Récupérer ou créer le client par EMAIL
        Client client = clientRepository.findByEmail(contratDto.getClientEmail())
                .orElseGet(() -> {
                    Client nouveauClient = new Client();
                    nouveauClient.setEmail(contratDto.getClientEmail());
                    nouveauClient.setNom("À compléter");
                    log.info("Nouveau client créé avec email: {}", contratDto.getClientEmail());
                    return clientRepository.save(nouveauClient);
                });

        // 3. Récupérer le propriétaire connecté (depuis le token)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Proprietaire proprietaire = proprietaireRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Propriétaire non trouvé avec email: " + email));

        // 4. Construire le contrat
        Contrat contrat = new Contrat();
        contrat.setTitre(contratDto.getTitre());
        contrat.setDateDebut(contratDto.getDateDebut());
        contrat.setDateFin(contratDto.getDateFin());
        contrat.setMontantLoyer(contratDto.getMontantLoyer());
        contrat.setCaution(contratDto.getCaution());
        contrat.setStatut(StatutContrat.EN_ATTENTE);
        contrat.setBien(bien);
        contrat.setClient(client);
        contrat.setProprietaire(proprietaire);  // ✅ Propriétaire connecté automatiquement

        // 5. Générer token de signature
        String token = UUID.randomUUID().toString();
        contrat.setTokenSignature(token);
        contrat.setTokenExpiration(LocalDateTime.now().plusHours(48));

        // 6. Sauvegarder
        Contrat savedContrat = contratRepository.save(contrat);
        log.info("Contrat sauvegardé avec id: {}", savedContrat.getId());

        // 7. Envoyer email de signature
        String lien = "http://localhost:4200/contrat/signer/" + token;

        try {
            emailService.envoyerLienSignature(
                    client.getEmail(),
                    client.getNom() != null ? client.getNom() : "Client",
                    savedContrat,
                    lien
            );
            log.info("Email envoyé avec succès à: {}", client.getEmail());
        } catch (Exception e) {
            log.error("Erreur lors de l'envoi de l'email: {}", e.getMessage());
        }

        return savedContrat;
    }





//    @Override
//    public Contrat createContrat(ContratDto contratDto) {
//
//        // 1. Récupérer le bien
//        Bien bien = bienRepository.findById(contratDto.getBienId())
//                .orElseThrow(() -> new RuntimeException("Bien non trouvé avec id: " + contratDto.getBienId()));
//
//        // 2. Récupérer le client
//        Client client = clientRepository.findById(contratDto.getClientId())
//                .orElseThrow(() -> new RuntimeException("Client non trouvé avec id: " + contratDto.getClientId()));
//
//        // 3. Récupérer le propriétaire
//        Proprietaire proprietaire = proprietaireRepository.findById(contratDto.getProprietaireId())
//                .orElseThrow(() -> new RuntimeException("Propriétaire non trouvé avec id: " + contratDto.getProprietaireId()));
//
//        // 4. Construire le contrat manuellement
//        Contrat contrat = new Contrat();
//        contrat.setTitre(contratDto.getTitre());
//        contrat.setDateDebut(contratDto.getDateDebut());
//        contrat.setDateFin(contratDto.getDateFin());
//        contrat.setMontantLoyer(contratDto.getMontantLoyer());
//        contrat.setCaution(contratDto.getCaution());
//        contrat.setStatut(StatutContrat.EN_ATTENTE);
//        contrat.setBien(bien);
//        contrat.setClient(client);
//        contrat.setProprietaire(proprietaire);
//
//        // 5. Générer token
//        String token = UUID.randomUUID().toString();
//        contrat.setTokenSignature(token);
//        contrat.setTokenExpiration(LocalDateTime.now().plusHours(48));
//
//        // 6. Sauvegarder
//        Contrat saved = contratRepository.save(contrat);
//
//        // 7. Envoyer email
//        String lien = "http://localhost:4200/contrat/signer/" + token;
//        try {
//            emailService.envoyerLienSignature(
//                    client.getEmail(),
//                    client.getNom(),
//                    saved,
//                    lien
//            );
//        } catch (Exception e) {
//            log.error("Erreur envoi email : {}", e.getMessage());
//        }
//
//        log.info("Contrat créé et email envoyé à {}", client.getEmail());
//        return saved;
//    }


    @Override
    public List<ContratDto> getAllContrats() {
        return contratRepository.findAll()
                .stream()
                .map(iContratMapper::fromContrat)
                .collect(Collectors.toList());
    }

    @Override
    public ContratDto getContratById(Long id) {
        Contrat contrat = contratRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé"));
        return iContratMapper.fromContrat(contrat);
    }

    @Override
    public ContratDto getContratByToken(String id) {
        Contrat contrat = contratRepository.findByTokenSignature(id)
                .orElseThrow(() -> new RuntimeException("Lien invalide"));

        if (contrat.getTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Lien expiré");
        }

        return iContratMapper.fromContrat(contrat);
    }

    @Override
    public void signerContrat(String id) throws Exception {
        // 1. Vérifier le token
        Contrat contrat = contratRepository.findByTokenSignature(id)
                .orElseThrow(() -> new RuntimeException("Lien invalide"));

        // 2. Vérifier expiration
        if (contrat.getTokenExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Lien expiré");
        }

        // 3. Mettre à jour statut
        contrat.setStatut(StatutContrat.EN_COURS);
        contratRepository.save(contrat);

        // 4. Notifier le propriétaire
        emailService.envoyerConfirmationSignature(
                contrat.getProprietaire().getEmail(),
                contrat.getProprietaire().getNom(),
                contrat
        );

        log.info("Contrat signé par {}", contrat.getClient().getEmail());
    }

    @Override
    public Contrat updateContrat(ContratDto contratDto) {
        Contrat contrat = contratRepository.findById(contratDto.getId())
                .orElseThrow(() -> new RuntimeException("Contrat non trouvé"));

        contrat.setTitre(contratDto.getTitre());
        contrat.setDateDebut(contratDto.getDateDebut());
        contrat.setDateFin(contratDto.getDateFin());
        contrat.setMontantLoyer(contratDto.getMontantLoyer());
        contrat.setCaution(contratDto.getCaution());
        contrat.setStatut(contratDto.getStatut());

        return contratRepository.save(contrat);
    }

    @Override
    public void deleteContrat(Long id) {
        if (!contratRepository.existsById(id)) {
            throw new RuntimeException("Contrat non trouvé");
        }
        contratRepository.deleteById(id);
    }
}