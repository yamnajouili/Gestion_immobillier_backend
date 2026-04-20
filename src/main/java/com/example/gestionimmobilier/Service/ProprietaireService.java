package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ProprietaireDto;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.Proprietaire;
import com.example.gestionimmobilier.Entity.Token;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Enum.Role;
import com.example.gestionimmobilier.Enum.TokenType;
import com.example.gestionimmobilier.Repository.ClientRepository;
import com.example.gestionimmobilier.Repository.ProprietaireRepository;
import com.example.gestionimmobilier.Repository.TokenRepository;
import com.example.gestionimmobilier.Security.JwtService;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProprietaireService implements IProprietaireService{
    private ProprietaireRepository proprietaireRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public List<Proprietaire> getAllProprietaires() {
        return proprietaireRepository.findAll();
    }

    @Override
    public Optional<Proprietaire> getProprietairetById(Long id) {
        return Optional.empty();
    }

    @Override
    public AuthenticationResponse register(ProprietaireDto request) {

        if (proprietaireRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already registered");
        }

        Proprietaire proprietaire = Proprietaire.builder()
                .nom(request.getNom())
                .poste(request.getPoste())
                .email(request.getEmail())
                .telephone(request.getTelephone())
                .role(Role.PROPRIETAIRE)
                .password(passwordEncoder.encode(request.getPassword()))
                .typeProprietaire(request.getTypeProprietaire())
                .adresseProfessionnelle(request.getAdresseProfessionnelle())
                .numeroSiret(request.getNumeroSiret())
                .nomAgence(request.getNomAgence())
                .build();

        Proprietaire savedProprietaire = proprietaireRepository.save(proprietaire);

        String jwtToken = jwtService.generateToken(savedProprietaire);
        String refreshToken = jwtService.generateRefreshToken(savedProprietaire);

        saveUserToken(savedProprietaire, jwtToken);

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
    public void deleteProprietaire(Long id) {

    }
}
