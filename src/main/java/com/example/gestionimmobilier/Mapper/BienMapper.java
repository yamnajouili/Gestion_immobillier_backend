package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.BienDto;
import com.example.gestionimmobilier.Entity.Bien;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BienMapper implements IBienMapper{
    private final ModelMapper modelMapper;
    @Override
    public BienDto fromBien(Bien bien) {
        return modelMapper.map(bien, BienDto.class);
    }

    @Override
    public Bien fromBienDTO(BienDto bienDto) {
        return modelMapper.map(bienDto, Bien.class);    }
}
