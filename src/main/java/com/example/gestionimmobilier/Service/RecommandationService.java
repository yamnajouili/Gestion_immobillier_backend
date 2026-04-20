package com.example.gestionimmobilier.Service;
import com.example.gestionimmobilier.Dtos.RecommandationDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Entity.PreferenceUtilisateur;
import com.example.gestionimmobilier.Entity.Recommandation;
import com.example.gestionimmobilier.Mapper.IRecommandationMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import com.example.gestionimmobilier.Repository.PrefUtilisateurRepository;
import com.example.gestionimmobilier.Repository.RecommandationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class RecommandationService  implements IRecommandationService{
    private final RecommandationRepository recommandationRepository;
    private final IRecommandationMapper iRecommandationMapper;
    private final BienRepository bienRepository;
    private final PrefUtilisateurRepository preferenceRepository;
    @Override
    public Recommandation create(RecommandationDto dto) {

        Recommandation recommandation = iRecommandationMapper.fromRecommandationDTO(dto);

        // 🔥 récupérer le bien depuis la base
        Bien bien = bienRepository.findById(dto.getBien().getId())
                .orElseThrow(() -> new RuntimeException("Bien not found"));

        // 🔥 récupérer la préférence
        PreferenceUtilisateur pref = preferenceRepository.findById(dto.getPreferenceUtilisateur().getId())
                .orElseThrow(() -> new RuntimeException("Preference not found"));

        recommandation.setBien(bien);
        recommandation.setPreferenceUtilisateur(pref);

        return recommandationRepository.save(recommandation);
    }

    @Override
    public List<RecommandationDto> getAllRecommandations() {
        return  recommandationRepository.findAll().stream().map(
                recommandation -> iRecommandationMapper.fromRecommandation(recommandation)
        ).collect(Collectors.toList());
    }

    @Override
    public RecommandationDto getRecommandationById(Long id) {
        Recommandation recommandation = recommandationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("recommandation non trouvé"));
        return iRecommandationMapper.fromRecommandation(recommandation);
    }

    @Override
    public Recommandation updateRecommandation(RecommandationDto recommandationDto) {
        Recommandation recommandation = iRecommandationMapper.fromRecommandationDTO(recommandationDto);
        return recommandationRepository.save(recommandation);
    }

    @Override
    public void deleteRecommandation(Long id) {


        if (!recommandationRepository.existsById(id)) {
            throw new RuntimeException("Recommandation non trouvé");
        }
        recommandationRepository.deleteById(id);

    }
}
