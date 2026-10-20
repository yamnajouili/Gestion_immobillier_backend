package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.PropositionAchatDto;
import com.example.gestionimmobilier.Entity.PropositionAchat;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PropositionAchatMapper implements IPropositionAchatMapper {


    private final ModelMapper modelMapper;

    @Override
    public PropositionAchatDto fromPropositionAchat(PropositionAchat propositionAchat) {
        return modelMapper.map(propositionAchat, PropositionAchatDto.class);
    }

    @Override
    public PropositionAchat fromPropositionAchatDTO(PropositionAchatDto propositionAchatDto) {
        return modelMapper.map(propositionAchatDto, PropositionAchat.class);
    }
}
