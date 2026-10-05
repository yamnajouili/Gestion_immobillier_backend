package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.AdminDto;
import com.example.gestionimmobilier.Entity.Admin;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.Enum.Role;
import com.example.gestionimmobilier.Mapper.AdminMapper;
import com.example.gestionimmobilier.Repository.AdminRepository;
import com.example.gestionimmobilier.Repository.UserRepository;
import lombok.AllArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AdminService implements IAdminService {
    private AdminRepository adminRepository;
    private UserRepository  userRepository;
    private AdminMapper   adminMapper;
    @Override
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }
    @Override
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }
    @Override
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }


    @Override
    public AdminDto updateProfile(AdminDto adminDto) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        System.out.println("EMAIL CONNECTE = " + email);


        Admin admin = adminRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Administrateur non trouvé")
                );

        admin.setNom(adminDto.getNom());
        admin.setTelephone(adminDto.getTelephone());
        admin.setPoste(adminDto.getPoste());

        Admin savedAdmin = adminRepository.save(admin);

        return adminMapper.fromAdmin(savedAdmin);
    }
}
