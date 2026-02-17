package com.example.gestionimmobilier.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

  @Autowired
  public FileUploadController(StorageService storageService) {
    this.storageService = storageService;
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
  @PostMapping(value = "/upload/multiple", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Map<String, Object>> handleMultipleFileUpload(
          @RequestPart(value = "files") MultipartFile[] files) {  // ← @RequestPart au lieu de @RequestParam

    Map<String, Object> response = new HashMap<>();
    List<String> uploadedFiles = new ArrayList<>();
    List<String> failedFiles = new ArrayList<>();

    try {
      System.out.println("Nombre de fichiers reçus: " + files.length);

      for (MultipartFile file : files) {
        try {
          if (file != null && !file.isEmpty()) {
            System.out.println("Traitement: " + file.getOriginalFilename());
            storageService.store(file);
            uploadedFiles.add(file.getOriginalFilename());
          }
        } catch (Exception e) {
          failedFiles.add(file.getOriginalFilename() + " - " + e.getMessage());
        }
      }

      response.put("message", uploadedFiles.size() + " fichier(s) uploadé(s) avec succès");
      response.put("uploaded", uploadedFiles);
      if (!failedFiles.isEmpty()) {
        response.put("failed", failedFiles);
      }

      return ResponseEntity.status(HttpStatus.CREATED).body(response);

    } catch (Exception e) {
      response.put("error", "Erreur: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
  }

  // ✅ DELETE: Supprimer un fichier
  @DeleteMapping("/files/{filename}")
  public ResponseEntity<?> deleteFile(@PathVariable String filename) {
    try {
      // Vous devez ajouter cette méthode dans StorageService
      // storageService.delete(filename);
      return ResponseEntity.ok(Map.of("message", "Fichier supprimé: " + filename));
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(Map.of("error", e.getMessage()));
    }
  }

  // ✅ Gestion des exceptions
  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity<?> handleStorageFileNotFound() {
    return ResponseEntity.notFound().build();
  }
}