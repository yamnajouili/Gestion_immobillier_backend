package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Entity.Client;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ClientMapper implements IClientMapper {

    private final ModelMapper modelMapper;

    @Override
    public ClientDto fromClient(Client client) {

        return modelMapper.map(client, ClientDto.class);
    }

    @Override
    public Client fromClientDTO(ClientDto clientDto) {

        return modelMapper.map(clientDto, Client.class);
    }
}
