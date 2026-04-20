package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.UserDto;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Mapper.IUserMapper;
import com.example.gestionimmobilier.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final IUserMapper iUserMapper;

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
}
