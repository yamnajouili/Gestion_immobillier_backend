package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Entity.Client;

public interface IClientMapper {
    ClientDto fromClient(Client client);

    Client fromClientDTO(ClientDto clientDto);
}
