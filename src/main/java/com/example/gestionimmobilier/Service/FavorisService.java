package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.FavorisDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.Favoris;
import com.example.gestionimmobilier.Mapper.IFavorisMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ClientRepository;
import com.example.gestionimmobilier.Repository.FavorisRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FavorisService implements IFavorisService {

    private final FavorisRepository favorisRepository;
    private final BienRepository bienRepository;
    private final ClientRepository clientRepository;
    private final IFavorisMapper iFavorisMapper;

    @Override
    public Favoris createFavoris(FavorisDto dto) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        Bien bien = bienRepository.findById(dto.getBienId())
                .orElseThrow(() -> new RuntimeException("Bien not found"));

        // 🔥 CHECK SI EXISTE DÉJÀ
        Optional<Favoris> existing = favorisRepository
                .findByClientIdAndBienId(client.getId(), bien.getId());

        if (existing.isPresent()) {
            return existing.get(); // 🚫 empêche doublon
        }

        Favoris favoris = new Favoris();
        favoris.setDateAjout(dto.getDateAjout());
        favoris.setClient(client);
        favoris.setBien(bien);

        return favorisRepository.save(favoris);
    }
//    @Override
//    public Favoris createFavoris(FavorisDto dto) {
//
//        // 🔥 1. récupérer email depuis Spring Security
//        String email = SecurityContextHolder
//                .getContext()
//                .getAuthentication()
//                .getName();
//
//        // 🔥 2. trouver le client connecté
//        Client client = clientRepository.findByEmail(email)
//                .orElseThrow(() -> new RuntimeException("Client not found"));
//
//        // 🔥 3. récupérer le bien
//        Bien bien = bienRepository.findById(dto.getBienId())
//                .orElseThrow(() -> new RuntimeException("Bien not found"));
//
//        // 🔥 4. créer favoris
//        Favoris favoris = new Favoris();
//        favoris.setDateAjout(dto.getDateAjout());
//        favoris.setClient(client);
//        favoris.setBien(bien);
//
//        return favorisRepository.save(favoris);
//    }

    @Override
    public List<FavorisDto> getAllFavoris() {
        return  favorisRepository.findAll().stream().map(
                favoris -> iFavorisMapper.fromFavoris(favoris)
        ).collect(Collectors.toList());
    }

    @Override
    public FavorisDto getFavorisById(Long id) {
        Favoris favoris = favorisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Favoris non trouvé"));
        return iFavorisMapper.fromFavoris(favoris);
    }

    @Override
    public Favoris updateFavoris(FavorisDto favorisDto) {
        Favoris favoris = iFavorisMapper.fromFavorisDTO(favorisDto);
        return favorisRepository.save(favoris);
    }

    @Override
    @Transactional
    public void deleteFavoris(Long id) {
        if (!favorisRepository.existsById(id)) {
            throw new RuntimeException("Favoris non trouvé");
        }
        favorisRepository.deleteById(id);

    }

    @Override
    public void deleteFavorisByBienId(Long bienId) {
        // Récupérer le client connecté
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Client client = clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        // Trouver le favoris par clientId et bienId
        Favoris favoris = favorisRepository.findByClientIdAndBienId(client.getId(), bienId)
                .orElseThrow(() -> new RuntimeException("Favoris non trouvé pour ce bien"));

        // Supprimer le favoris
        favorisRepository.deleteById(favoris.getId());
    }
}
