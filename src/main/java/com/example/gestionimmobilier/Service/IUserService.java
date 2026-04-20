package com.example.gestionimmobilier.Service;

import com.example.gestionimmobilier.Dtos.UserDto;


import java.util.List;

public interface IUserService {


    List<UserDto> getAllUsers();


    UserDto getLoggedInUser();



}
