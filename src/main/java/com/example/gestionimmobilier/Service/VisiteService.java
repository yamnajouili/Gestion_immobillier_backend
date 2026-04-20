package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.VisiteDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.Visite;
import com.example.gestionimmobilier.Mapper.IBienMapper;
import com.example.gestionimmobilier.Mapper.IVisiteMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ClientRepository;
import com.example.gestionimmobilier.Repository.VisiteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VisiteService implements IVisiteService{

    private final VisiteRepository visiteRepository;
    private final IVisiteMapper iVisiteMapper;
    private final BienRepository bienRepository;
    private final ClientRepository clientRepository;
    @Override
    public Visite createVisite(VisiteDto dto) {
        // Vérifier que le bienId est fourni
        if (dto.getBienId() == null) {
            throw new RuntimeException("Le Bien est obligatoire pour créer une visite");
        }

        // Vérifier que le clientId est fourni (NOUVEAU)
        if (dto.getClientId() == null) {
            throw new RuntimeException("Le Client est obligatoire pour créer une visite");
        }

        Visite visite = new Visite();
        visite.setDateProposee(dto.getDateProposee());
        visite.setDateConfirme(dto.getDateConfirme());
        visite.setStatut(dto.getStatut());
        visite.setCommentaire(dto.getCommentaire());

        // Récupérer le Bien existant depuis la DB
        Bien bien = bienRepository.findById(dto.getBienId())
                .orElseThrow(() -> new RuntimeException("Bien introuvable avec id: " + dto.getBienId()));
        visite.setBien(bien);

        // Récupérer le Client existant depuis la DB (NOUVEAU)
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client introuvable avec id: " + dto.getClientId()));
        visite.setClient(client);  // ← Lien vers le client

        return visiteRepository.save(visite);
    }
    @Override
    public List<VisiteDto> getAllVisites() {
        return  visiteRepository.findAll().stream().map(
                visite -> iVisiteMapper.fromVisite(visite)
        ).collect(Collectors.toList());
    }

    @Override
    public VisiteDto getVisiteById(Long id) {
        Visite visite = visiteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Visite non trouvé"));
        return iVisiteMapper.fromVisite(visite);
    }

    @Override
    public Visite updateVisite(VisiteDto visiteDto) {
        Visite visite = iVisiteMapper.fromVisiteDTO(visiteDto);
        return visiteRepository.save(visite);
    }

    @Override
    public void deleteVisite(Long id) {
        if (!visiteRepository.existsById(id)) {
            throw new RuntimeException("Visite non trouvé");
        }
        visiteRepository.deleteById(id);

    }
}
