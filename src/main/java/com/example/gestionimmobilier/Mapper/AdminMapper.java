package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.AdminDto;
import com.example.gestionimmobilier.Entity.Admin;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminMapper implements IAdminMapper {
    private final ModelMapper modelMapper;

    @Override
    public AdminDto fromAdmin(Admin admin) {
        return modelMapper.map(admin, AdminDto.class);
    }

    @Override
    public Admin fromAdminDTO(AdminDto adminDto) {
        return modelMapper.map(adminDto, Admin.class);
    }
   
}
