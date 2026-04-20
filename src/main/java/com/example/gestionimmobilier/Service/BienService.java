package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Entity.Bien;
import com.example.gestionimmobilier.Mapper.IBienMapper;
import com.example.gestionimmobilier.Repository.BienRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BienService implements IBienService{
    private final BienRepository bienRepository;
    private final IBienMapper iBienMapper;
    @Override
    public Bien createBien(BienDto bienDto) {

        Bien bien = iBienMapper.fromBienDTO(bienDto);

        return bienRepository.save(bien);
    }

    @Override
    public List<BienDto> getAllBiens() {
        return  bienRepository.findAll().stream().map(
                bien -> iBienMapper.fromBien(bien)
        ).collect(Collectors.toList());
    }

    @Override
    public BienDto getBienById(Long id) {
        Bien bien = bienRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bien non trouvé"));
        return iBienMapper.fromBien(bien);
    }

    @Override
    public Bien updateBien(BienDto bienDto) {
        Bien bien = iBienMapper.fromBienDTO(bienDto);
        return bienRepository.save(bien);
    }

    @Override
    public void deleteBien(Long id) {

        if (!bienRepository.existsById(id)) {
            throw new RuntimeException("Bien non trouvé");
        }
        bienRepository.deleteById(id);

    }
}
