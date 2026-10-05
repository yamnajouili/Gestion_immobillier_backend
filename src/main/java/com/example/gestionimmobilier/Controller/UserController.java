package com.example.gestionimmobilier.Controller;


import com.example.gestionimmobilier.Dtos.ChangePasswordRequest;
import com.example.gestionimmobilier.Dtos.UserDto;
import com.example.gestionimmobilier.Service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Tag(name = "Users", description = "CRUD Users")

@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserService iUserService;



    @GetMapping("/current-user")
    public UserDto getCurrentUser() {
        return iUserService.getLoggedInUser();
    }
    @PutMapping("/change-password")
    public ResponseEntity<Map<String, String>> changePassword(
            @RequestBody ChangePasswordRequest request,
            Principal connectedUser

    ) {

        iUserService.changePassword(
                request,
                connectedUser
        );

        Map<String, String> response = new HashMap<>();

        response.put(
                "message",
                "Mot de passe modifié avec succès"
        );

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {

        return ResponseEntity.ok(
                iUserService.getAllUsers()
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {

        iUserService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }











}


