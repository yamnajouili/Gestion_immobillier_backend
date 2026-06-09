package com.example.gestionimmobilier.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.ImageFile;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.ImageFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.gestionimmobilier.storage.StorageFileNotFoundException;
import com.example.gestionimmobilier.storage.StorageService;

@RestController  // ← OK pour API REST


@RequestMapping("/api")  // ← Ajouté pour organiser
public class FileUploadController {

  private final StorageService storageService;
  private final BienRepository bienRepository;
  private final ImageFileRepository imageFileRepository;
  @Autowired
  public FileUploadController(StorageService storageService, BienRepository bienRepository, ImageFileRepository imageFileRepository) {
    this.storageService = storageService;
    this.bienRepository = bienRepository;
    this.imageFileRepository = imageFileRepository;
  }

  // ✅ GET: Liste tous les fichiers (retourne JSON)
  @GetMapping("/files")
  public ResponseEntity<?> listUploadedFiles() {
    var files = storageService.loadAll()
            .map(path -> {
              Map<String, String> fileInfo = new HashMap<>();
              fileInfo.put("filename", path.getFileName().toString());
              fileInfo.put("url", "/api/files/" + path.getFileName().toString());
              return fileInfo;
            })
            .collect(Collectors.toList());

    return ResponseEntity.ok(files);
  }

  // ✅ GET: Télécharger un fichier
  @GetMapping("/files/{filename:.+}")
  public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
    Resource file = storageService.loadAsResource(filename);

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"")
            .body(file);
  }

  // ✅ POST: Upload un fichier (retourne JSON)
  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, Object>> handleFileUpload(
          @RequestParam("file") MultipartFile file) {

    Map<String, Object> response = new HashMap<>();

    try {
      if (file.isEmpty()) {
        response.put("error", "Fichier vide");
        return ResponseEntity.badRequest().body(response);
      }

      storageService.store(file);

      response.put("message", "Fichier uploadé avec succès");
      response.put("filename", file.getOriginalFilename());
      response.put("size", file.getSize());
      response.put("url", "/api/files/" + file.getOriginalFilename());

      return ResponseEntity.status(HttpStatus.CREATED).body(response);

    } catch (Exception e) {
      response.put("error", "Erreur: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  // ✅ POST: Upload plusieurs fichiers




  @PostMapping(value = "/upload/multiple/{bienId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<?> uploadImages(
          @PathVariable Long bienId,
          @RequestPart("files") MultipartFile[] files) {

    Bien bien = bienRepository.findById(bienId)
            .orElseThrow(() -> new RuntimeException("Bien introuvable"));

    for (int i = 0; i < files.length; i++) {
      MultipartFile file = files[i];

      if (!file.isEmpty()) {
        // ✔ 1. stocker fichier et récupérer le nom généré
        String storedFileName = storageService.store(file);

        // ✔ 2. Construire l'URL complète
        String baseUrl = "http://localhost:8080/uploads/"; // À adapter selon votre configuration
        String fullUrl = baseUrl + storedFileName;

        // ✔ 3. créer image en DB avec l'URL complète
        ImageFile image = new ImageFile();
        image.setUrl(fullUrl); // Stocke l'URL complète
        image.setEstPrincipale(i == 0);
        image.setOrdre(i);
        image.setBien(bien);

        imageFileRepository.save(image);
      }
    }

    return ResponseEntity.ok("Images ajoutées avec succès");
  }

//  @PostMapping(value = "/upload/multiple/{bienId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//  public ResponseEntity<?> uploadImages(
//          @PathVariable Long bienId,
//          @RequestPart("files") MultipartFile[] files) {
//
//    Bien bien = bienRepository.findById(bienId)
//            .orElseThrow(() -> new RuntimeException("Bien introuvable"));
//
//    for (int i = 0; i < files.length; i++) {
//
//      MultipartFile file = files[i];
//
//      if (!file.isEmpty()) {
//
//        // ✔ 1. stocker fichier
//        storageService.store(file);
//
//        // ✔ 2. créer image en DB
//        ImageFile image = new ImageFile();
//        image.setUlr(file.getOriginalFilename());
//        image.setEstPrincipale(i == 0);
//        image.setOrdre(i);
//        image.setBien(bien);
//
//        imageFileRepository.save(image);
//      }
//    }
//
//    return ResponseEntity.ok("Images ajoutées avec succès");
//  }





  // ✅ DELETE: Supprimer un fichier
  @DeleteMapping("/files/{filename}")
  public ResponseEntity<?> deleteFile(@PathVariable String filename) {
    try {

      String url ="http://localhost:8080/uploads/"+ filename;

      // 🔥 vérifier en base
      ImageFile image = imageFileRepository.findByUrl(url)
              .orElseThrow(() -> new RuntimeException("Image non trouvée en BD"));

      // 🔥 supprimer fichier
      storageService.delete(filename);

      // 🔥 supprimer BD
      imageFileRepository.delete(image);

      return ResponseEntity.ok("Supprimé");

    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(e.getMessage());
    }
  }

  // ✅ Gestion des exceptions
  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound() {
    return ResponseEntity.notFound().build();
  }
}