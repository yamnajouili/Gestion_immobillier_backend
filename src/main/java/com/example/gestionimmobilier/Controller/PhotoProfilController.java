package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Repository.UserRepository;
import com.example.gestionimmobilier.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/profil")
@RequiredArgsConstructor
public class PhotoProfilController {

    private final UserRepository userRepository;
    private final StorageService storageService;

    @PostMapping(value = "/photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> modifierPhoto(@RequestParam("file") MultipartFile file, Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401)
                    .body(Map.of("message", "Non authentifié"));
        }

        User user = userRepository
                .findByEmail(authentication.getName())
                .orElseThrow(() ->
                        new RuntimeException("Utilisateur introuvable")
                );

        if (file.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Image vide"));
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Maximum 5 Mo"));
        }

        if (!Set.of("image/jpeg", "image/png")
                .contains(file.getContentType())) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "JPG ou PNG uniquement"));
        }

        // Réutiliser ton service sans le modifier
        String filename = storageService.store(file);

        user.setPhotoProfil("/api/files/" + filename);

        userRepository.save(user);

        return ResponseEntity.ok(Map.of(
                "message", "Photo modifiée avec succès",
                "photoProfil", user.getPhotoProfil()
        ));
    }
}