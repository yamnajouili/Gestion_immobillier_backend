package com.example.gestionimmobilier.Mapper;
import com.example.gestionimmobilier.Dtos.PreferenceUtilisateurDto;
import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PreUtilisateurMapper implements IPreUtilisateurMapper{
    private final ModelMapper modelMapper;
    @Override
    public PreferenceUtilisateurDto fromPreUtilisateur(PreferenceUtilisateur preferenceUtilisateur) {
        return modelMapper.map(preferenceUtilisateur, PreferenceUtilisateurDto.class);
    }

    @Override
    public PreferenceUtilisateur fromPreUtilisateurDTO(PreferenceUtilisateurDto preferenceUtilisateurDto) {
        return modelMapper.map(preferenceUtilisateurDto, PreferenceUtilisateur.class);
    }
}
