package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ChangePasswordRequest;
import com.example.gestionimmobilier.Dtos.UserDto;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Mapper.IUserMapper;
import com.example.gestionimmobilier.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IUserMapper iUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        return  userRepository.findAll().stream().map(
                user -> iUserMapper.fromUser(user)
        ).collect(Collectors.toList());
    }

    @Override
    public UserDto getLoggedInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("Utilisateur non authentifié");
        }

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        return iUserMapper.fromUser(user);
    }



    @Override
    public void changePassword(ChangePasswordRequest request, Principal connectedUser) {

        User user = (User)
                ((UsernamePasswordAuthenticationToken) connectedUser)
                        .getPrincipal();

        // Vérifier le mot de passe actuel
        if (!passwordEncoder.matches(
                request.getCurrentPassword(),
                user.getPassword()
        )) {
            throw new IllegalStateException(
                    "Mot de passe actuel incorrect"
            );
        }

        // Vérifier la confirmation
        if (!request.getNewPassword().equals(
                request.getConfirmationPassword()
        )) {
            throw new IllegalStateException(
                    "Les nouveaux mots de passe ne correspondent pas"
            );
        }

        // Encoder le nouveau mot de passe
        user.setPassword(
                passwordEncoder.encode(
                        request.getNewPassword()
                )
        );

        // Enregistrer
        userRepository.save(user);
    }
}
