package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.ImageFile;
import com.example.gestionimmobilier.Entity.Proprietaire;
import com.example.gestionimmobilier.Mapper.IBienMapper;
import com.example.gestionimmobilier.Repository.*;
import com.example.gestionimmobilier.handler.BienHasContratException;
import com.example.gestionimmobilier.storage.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@AllArgsConstructor
public class BienService implements IBienService{
    private final BienRepository bienRepository;
    private final ContratRepository contratRepository;
    private final IBienMapper iBienMapper;
    private final StorageService storageService;
    private final ImageFileRepository imageFileRepository;
    private final ObjectMapper objectMapper;
    private final ProprietaireRepository proprietaireRepository;
    @Override
    public Bien createBien(BienDto bienDto) {

        Bien bien = iBienMapper.fromBienDTO(bienDto);

        return bienRepository.save(bien);
    }

    @Override
    public Bien createBienWithImages(String bienJson, MultipartFile[] files) {

        try {
            // 🔥 JSON -> DTO
            BienDto request = objectMapper.readValue(bienJson, BienDto.class);

            // 🚨 anti doublon
            if (bienRepository.existsByTitreAndAdresse(
                    request.getTitre(),
                    request.getAdresse())) {
                throw new RuntimeException("Bien déjà existant");
            }

            // 🔐 récupérer user connecté
            String email = SecurityContextHolder
                    .getContext()
                    .getAuthentication()
                    .getName();

            Proprietaire proprietaire = proprietaireRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Propriétaire introuvable"));

            // 🏠 création bien
            Bien bien = Bien.builder()
                    .titre(request.getTitre())
                    .description(request.getDescription())
                    .prix(request.getPrix())
                    .surface(request.getSurface())
                    .type(request.getType())
                    .adresse(request.getAdresse())
                    .ville(request.getVille())
                    .latitude(request.getLatitude())
                    .longitude(request.getLongitude())
                    .disponible(request.getDisponible())
                    .proprietaire(proprietaire) // 🔥 LIAISON IMPORTANTE
                    .build();

            Bien savedBien = bienRepository.save(bien);

            // 🖼️ upload images
            if (files != null) {
                for (int i = 0; i < files.length; i++) {

                    MultipartFile file = files[i];

                    if (file != null && !file.isEmpty()) {

                        String fileName = storageService.store(file);
                        String url = "http://localhost:8081/uploads/" + fileName;

                        ImageFile image = new ImageFile();
                        image.setUrl(url);
                        image.setEstPrincipale(i == 0);
                        image.setOrdre(i);
                        image.setBien(savedBien);

                        imageFileRepository.save(image);
                    }
                }
            }

            return savedBien;

        } catch (Exception e) {
            throw new RuntimeException("Erreur création bien", e);
        }
    }
    @Override
    public List<BienDto> getAllBiens() {
        return  bienRepository.findAll().stream().map(
                bien -> iBienMapper.fromBien(bien)
        ).collect(Collectors.toList());
    }

    @Override
    public List<BienDto> getMyBiens() {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        return bienRepository.findByProprietaireEmail(email)
                .stream()
                .map(iBienMapper::fromBien)
                .toList();
    }

    @Override
    public BienDto getBienById(Long id) {
        Bien bien = bienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bien non trouvé"));
        return iBienMapper.fromBien(bien);
    }

    @Override
    public Bien updateBien(BienDto bienDto) {

        Bien bien = bienRepository.findById(bienDto.getId())
                .orElseThrow(() ->
                        new RuntimeException("Bien non trouvé avec id : " + bienDto.getId())
                );

        bien.setTitre(bienDto.getTitre());
        bien.setDescription(bienDto.getDescription());
        bien.setPrix(bienDto.getPrix());
        bien.setSurface(bienDto.getSurface());
        bien.setType(bienDto.getType());
        bien.setAdresse(bienDto.getAdresse());
        bien.setVille(bienDto.getVille());
        bien.setLatitude(bienDto.getLatitude());
        bien.setLongitude(bienDto.getLongitude());
        bien.setDisponible(bienDto.getDisponible());


        return bienRepository.save(bien);
    }

    @Override
    public void deleteBien(Long id) {

        if (!bienRepository.existsById(id)) {
            throw new RuntimeException("Bien introuvable");
        }

        if (contratRepository.existsByBienId(id)) {
            throw new BienHasContratException(
                    "Impossible de supprimer ce bien car il est associé à un contrat."
            );
        }

        bienRepository.deleteById(id);
    }
}
