package com.example.gestionimmobilier.Dtos;

import com.example.gestionimmobilier.Enum.Role;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserDto {

        private Long id;
        private String nom;
        private double telephone;
        private String poste;
        private String email;
        private String password;
        private Role role;



}
