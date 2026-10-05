package com.example.gestionimmobilier;

import com.example.gestionimmobilier.Entity.Admin;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Enum.Role;
import com.example.gestionimmobilier.Repository.AdminRepository;
import com.example.gestionimmobilier.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
@RequiredArgsConstructor
public class GestionImmobilierApplication {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    public static void main(String[] args) {

        SpringApplication.run(
                GestionImmobilierApplication.class,
                args
        );
    }
//
//
//    @Bean
//    CommandLineRunner createAdmin() {
//
//        return args -> {
//
//            String email = "admin@gmail.com";
//
//            if (userRepository.findByEmail(email).isEmpty()) {
//
//                Admin admin = new Admin();
//
//                admin.setNom("Administrateur");
//                admin.setEmail(email);
//
//                admin.setPassword(
//                        passwordEncoder.encode("0000")
//                );
//
//                admin.setRole(Role.ADMIN);
//
//                adminRepository.save(admin);
//
//                System.out.println(
//                        "✅ Compte ADMIN créé : " + email
//                );
//
//            } else {
//
//                System.out.println(
//                        "ℹ️ Compte ADMIN existe déjà"
//                );
//            }
//        };
//    }
}