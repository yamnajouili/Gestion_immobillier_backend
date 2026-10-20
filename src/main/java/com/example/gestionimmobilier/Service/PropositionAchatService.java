package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.PropositionAchatDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.PropositionAchat;
import com.example.gestionimmobilier.Entity.Proprietaire;
import com.example.gestionimmobilier.Enum.StatutProposition;
import com.example.gestionimmobilier.Enum.TypeTransaction;
import com.example.gestionimmobilier.Mapper.IPropositionAchatMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ClientRepository;
import com.example.gestionimmobilier.Repository.PropositionAchatRepository;
import com.example.gestionimmobilier.Repository.ProprietaireRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
@Service
@AllArgsConstructor
public class PropositionAchatService implements IPropositionAchatService{
    private final PropositionAchatRepository propositionAchatRepository;
    private final ClientRepository clientRepository;
    private final BienRepository bienRepository;
    private final ProprietaireRepository proprietaireRepository;
    private final IPropositionAchatMapper iPropositionAchatMapper;

    @Override
    public PropositionAchatDto ajouter(PropositionAchatDto dto) {

        // Récupérer le client connecté avec JWT
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Client introuvable")
                );

        Bien bien = bienRepository.findById(dto.getBienId())
                .orElseThrow(() ->
                        new RuntimeException("Bien introuvable")
                );

        // Vérifier que le bien est en vente
        if (bien.getTypeTransaction() != TypeTransaction.VENTE) {
            throw new RuntimeException(
                    "La proposition d'achat concerne uniquement les biens en vente"
            );
        }

        if (dto.getMontantPropose() == null
                || !Double.isFinite(dto.getMontantPropose())
                || dto.getMontantPropose() <= 0) {
            throw new RuntimeException("Montant proposé invalide");
        }

        // Utiliser le Mapper
        PropositionAchat proposition =
                iPropositionAchatMapper.fromPropositionAchatDTO(dto);

        proposition.setId(null);
        proposition.setClient(client);
        proposition.setBien(bien);
        proposition.setDateProposition(LocalDateTime.now());
        proposition.setStatut(StatutProposition.EN_ATTENTE);

        PropositionAchat saved =
                propositionAchatRepository.save(proposition);

        return iPropositionAchatMapper.fromPropositionAchat(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PropositionAchatDto> getPropositionsByClient(Long clientId) {

        return propositionAchatRepository.findByClientId(clientId)
                .stream()
                .map(iPropositionAchatMapper::fromPropositionAchat)
                .toList();
    }
    @Override
    public List<PropositionAchatDto> getPropositionsProprietaire() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Proprietaire proprietaire = proprietaireRepository
                .findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Propriétaire introuvable")
                );

        List<PropositionAchat> propositions = propositionAchatRepository
                .findByBien_Proprietaire_Id(proprietaire.getId());

        return propositions.stream()
                .map(proposition -> {
                    PropositionAchatDto dto =
                            iPropositionAchatMapper.fromPropositionAchat(proposition);

                    dto.setClientId(proposition.getClient().getId());
                    dto.setBienId(proposition.getBien().getId());

                    return dto;
                })
                .toList();
    }


    @Override
    @Transactional(readOnly = true)
    public List<PropositionAchatDto> getPropositionsByBien(Long bienId) {

        return propositionAchatRepository.findByBienId(bienId)
                .stream()
                .map(iPropositionAchatMapper::fromPropositionAchat)
                .toList();
    }

    @Override
    public PropositionAchatDto accepter(Long id) {

        return changerStatut(id, StatutProposition.ACCEPTEE);
    }

    @Override
    public PropositionAchatDto refuser(Long id) {

        return changerStatut(id, StatutProposition.REFUSEE);
    }

    private PropositionAchatDto changerStatut(
            Long id, StatutProposition statut) {

        PropositionAchat proposition = propositionAchatRepository
                .findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Proposition introuvable")
                );

        if (proposition.getStatut() != StatutProposition.EN_ATTENTE) {
            throw new RuntimeException(
                    "Cette proposition a déjà été traitée"
            );
        }

        proposition.setStatut(statut);

        PropositionAchat saved =
                propositionAchatRepository.save(proposition);

        return iPropositionAchatMapper.fromPropositionAchat(saved);
    }







}
