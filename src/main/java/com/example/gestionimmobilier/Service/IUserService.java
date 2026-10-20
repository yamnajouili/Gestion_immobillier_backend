package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.ChangePasswordRequest;
import com.example.gestionimmobilier.Dtos.UserDetailDto;
import com.example.gestionimmobilier.Dtos.UserDto;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface IUserService {


    List<UserDto> getAllUsers();
    UserDto getLoggedInUser();
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

    Optional<UserDto> getUserById(Long id);

    UserDetailDto getUserDetailById(Long id);

    void deleteUser(Long id);

}
