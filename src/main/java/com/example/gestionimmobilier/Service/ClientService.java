package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Entity.Client;

import com.example.gestionimmobilier.Entity.Favoris;
import com.example.gestionimmobilier.Entity.Token;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Enum.Role;
import com.example.gestionimmobilier.Enum.TokenType;
import com.example.gestionimmobilier.Repository.ClientRepository;
import com.example.gestionimmobilier.Repository.FavorisRepository;
import com.example.gestionimmobilier.Repository.TokenRepository;
import com.example.gestionimmobilier.Security.JwtService;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class ClientService implements IClientService{

    private ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final FavorisRepository favorisRepository;
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }



    public AuthenticationResponse register(ClientDto request) {
        if (clientRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Client client = Client.builder()
                .nom(request.getNom())
                .poste(request.getPoste())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .role(Role.CLIENT) // force côté backend
                .password(passwordEncoder.encode(request.getPassword()))
                .build();

        Client savedClient = clientRepository.save(client);

        String jwtToken = jwtService.generateToken(savedClient);
        String refreshToken = jwtService.generateRefreshToken(savedClient);
        saveUserToken(savedClient, jwtToken);

        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }




    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }



    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);

    }




}
