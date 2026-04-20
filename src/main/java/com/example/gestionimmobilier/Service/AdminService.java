package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Entity.Admin;
import com.example.gestionimmobilier.Repository.AdminRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
@AllArgsConstructor
public class AdminService implements IAdminService {
    private AdminRepository adminRepository;
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
}
