package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.AdminDto;
import com.example.gestionimmobilier.Entity.Admin;

import java.util.List;
import java.util.Optional;

public interface IAdminService {
    List<Admin> getAllAdmins();

    Optional<Admin> getAdminById(Long id);

    Admin saveAdmin(Admin admin);

    void deleteAdmin(Long id);
}
