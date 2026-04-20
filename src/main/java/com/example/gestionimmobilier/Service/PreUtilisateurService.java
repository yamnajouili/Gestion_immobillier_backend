package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Dtos.PreferenceUtilisateurDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;
import com.example.gestionimmobilier.Mapper.IBienMapper;
import com.example.gestionimmobilier.Mapper.IPreUtilisateurMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.PrefUtilisateurRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PreUtilisateurService implements IPreUtilisateurService{

    private final PrefUtilisateurRepository prefUtilisateurRepository;
    private final IPreUtilisateurMapper iPreUtilisateurMapper;
    @Override
    public PreferenceUtilisateur createPreUtilisateur(PreferenceUtilisateurDto preferenceUtilisateurDto) {

        PreferenceUtilisateur preferenceUtilisateur = iPreUtilisateurMapper.fromPreUtilisateurDTO(preferenceUtilisateurDto);

        return prefUtilisateurRepository.save(preferenceUtilisateur);
    }

    @Override
    public List<PreferenceUtilisateurDto> getAllPreUtilisateurs() {
        return  prefUtilisateurRepository.findAll().stream().map(
                preUtilisateur -> iPreUtilisateurMapper.fromPreUtilisateur(preUtilisateur)
        ).collect(Collectors.toList());
    }

    @Override
    public PreferenceUtilisateurDto getPreUtilisateurById(Long id) {
        PreferenceUtilisateur preferenceUtilisateur = prefUtilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("PreUtilsateur non trouvé"));
        return iPreUtilisateurMapper.fromPreUtilisateur(preferenceUtilisateur);
    }

    @Override
    public PreferenceUtilisateur updatePreUtilisateur(PreferenceUtilisateurDto preferenceUtilisateurDto) {
        PreferenceUtilisateur preferenceUtilisateur = iPreUtilisateurMapper.fromPreUtilisateurDTO(preferenceUtilisateurDto);
        return prefUtilisateurRepository.save(preferenceUtilisateur);
    }

    @Override
    public void deletePreUtilisateur(Long id) {
        if (!prefUtilisateurRepository.existsById(id)) {
            throw new RuntimeException("preUtilisateur  non trouvé");
        }
        prefUtilisateurRepository.deleteById(id);

    }
}
