package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ClientDto;
import com.example.gestionimmobilier.Entity.Admin;
import com.example.gestionimmobilier.Entity.Client;
import com.example.gestionimmobilier.Entity.Favoris;
import com.example.gestionimmobilier.Entity.User;
import com.example.gestionimmobilier.auth.AuthenticationResponse;
import com.example.gestionimmobilier.auth.RegisterRequest;

import java.util.List;
import java.util.Optional;

public interface IClientService {



    List<Client> getAllClients();

    Optional<Client> getClientById(Long id);

    AuthenticationResponse register(ClientDto request);


    void deleteClient(Long id);



}
