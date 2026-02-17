package com.example.gestionimmobilier.auth;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.gestionimmobilier.Entity.Token;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Enum.TokenType;
import com.example.gestionimmobilier.Repository.TokenRepository;
import com.example.gestionimmobilier.Repository.UserRepository;
import com.example.gestionimmobilier.Security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.io.IOException;



@Service
@RequiredArgsConstructor
public class AuthenticationService {
  private final UserRepository repository;
  private final TokenRepository tokenRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

//  public AuthenticationResponse register(RegisterRequest request) {
//    var user = User.builder()
//            .id(request.getId())
//        .nom(request.getNom())
//        .poste(request.getPoste())
//        .email(request.getEmail())
//            .telephone(request.getTelephone())
//        .password(passwordEncoder.encode(request.getPassword()))
//        .build();
//    if(request.getId()>0 && request.getRole()== Role.EMPLOYER){
//        .role(request.getRole())
//    }
//    var savedUser = repository.save(user);
//    var jwtToken = jwtService.generateToken(user);
//    var refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//
//    return AuthenticationResponse.builder()
//        .accessToken(jwtToken)
//            .refreshToken(refreshToken)
//        .build();
//  }
//  public AuthenticationResponse register(RegisterRequest request) {
//      User userRegister = repository.findById(request.getId()).orElse(null);
//
//    User.UserBuilder userBuilder = User.builder()
//            .id(request.getId())
//            .nom(request.getNom())
//            .poste(request.getPoste())
//            .email(request.getEmail())
//            .telephone(request.getTelephone());
//
//
//    if (userRegister != null ) {
//      userBuilder.role(userRegister.getRole());
//      userBuilder.password(userRegister.getPassword());
//
//    }else {
//      userBuilder.role(request.getRole());
//      userBuilder. password(passwordEncoder.encode(request.getPassword()));
//
//    }
//
//
//    User user = userBuilder.build();
//    User savedUser = repository.save(user);
//
//    String jwtToken = jwtService.generateToken(user);
//    String refreshToken = jwtService.generateRefreshToken(user);
//    saveUserToken(savedUser, jwtToken);
//
//    return AuthenticationResponse.builder()
//            .accessToken(jwtToken)
//            .refreshToken(refreshToken)
//            .build();
//  }

public AuthenticationResponse register(RegisterRequest request) {
  // Check if email is already registered
  if (repository.findByEmail(request.getEmail()).isPresent()) {
    throw new RuntimeException("Email already registered");
  }

  // Build new user
  User user = User.builder()
          .nom(request.getNom())
          .poste(request.getPoste())
          .email(request.getEmail())
          .telephone(request.getTelephone())
          .role(request.getRole())
          .password(passwordEncoder.encode(request.getPassword()))
          .build();

  // Save the new user
  User savedUser = repository.save(user);

  // Generate tokens
  String jwtToken = jwtService.generateToken(user);
  String refreshToken = jwtService.generateToken(user);
  saveUserToken(savedUser, jwtToken);

  return AuthenticationResponse.builder()
          .accessToken(jwtToken)
          .refreshToken(refreshToken)
          .build();
}
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getEmail(),
            request.getPassword()
        )
    );
    var user = repository.findByEmail(request.getEmail())
        .orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    var refreshToken = jwtService.generateRefreshToken(user);
    revokeAllUserTokens(user);
    saveUserToken(user, jwtToken);
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

  private void revokeAllUserTokens(User user) {
    var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
    if (validUserTokens.isEmpty())
      return;
    validUserTokens.forEach(token -> {
      token.setExpired(true);
      token.setRevoked(true);
    });
    tokenRepository.saveAll(validUserTokens);
  }

  public void refreshToken(
          HttpServletRequest request,
          HttpServletResponse response
  ) throws IOException {
    final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
    final String refreshToken;
    final String userEmail;
    if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
      return;
    }
    refreshToken = authHeader.substring(7);
    userEmail = jwtService.extractUsername(refreshToken);
    if (userEmail != null) {
      var user = this.repository.findByEmail(userEmail)
              .orElseThrow();
      if (jwtService.isTokenValid(refreshToken, user)) {
        var accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        var authResponse = AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
        new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
      }
    }
  }
}
