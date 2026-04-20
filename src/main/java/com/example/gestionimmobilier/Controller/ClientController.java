package com.example.gestionimmobilier.Controller;

import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Service.IClientService;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Client", description = "CRUD Client")
@AllArgsConstructor
@RequestMapping("/cliens")
public class ClientController {

    private IClientService iClientService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody ClientDto request) {
        return ResponseEntity.ok(iClientService.register(request));
    }



    @GetMapping("/all")
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(iClientService.getAllClients());
    }


}
