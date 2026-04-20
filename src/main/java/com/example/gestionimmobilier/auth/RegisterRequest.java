package com.example.gestionimmobilier.auth;


import com.example.gestionimmobilier.Enum.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private Long id;
  private String nom;
  private String poste;
  private String telephone;
  private String email;
  private String password;
  private Role role;
}
