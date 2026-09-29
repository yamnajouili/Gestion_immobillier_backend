package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ChangePasswordRequest;
import com.example.gestionimmobilier.Dtos.UserDto;


import java.security.Principal;
import java.util.List;

public interface IUserService {


    List<UserDto> getAllUsers();


    UserDto getLoggedInUser();
    void changePassword(ChangePasswordRequest request, Principal connectedUser);


}
