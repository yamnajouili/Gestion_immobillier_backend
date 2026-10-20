package com.example.gestionimmobilier.Mapper;


import com.example.gestionimmobilier.Dtos.PropositionAchatDto;
import com.example.gestionimmobilier.Entity.PropositionAchat;

public interface IPropositionAchatMapper {

    PropositionAchatDto fromPropositionAchat(PropositionAchat propositionAchat);
    PropositionAchat fromPropositionAchatDTO(PropositionAchatDto propositionAchatDto);
}
