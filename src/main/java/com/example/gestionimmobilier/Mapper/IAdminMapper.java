package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.AdminDto;
import com.example.gestionimmobilier.Entity.Admin;

public interface IAdminMapper {
    AdminDto fromAdmin(Admin admin);

    Admin fromAdminDTO(AdminDto adminDto);
}
