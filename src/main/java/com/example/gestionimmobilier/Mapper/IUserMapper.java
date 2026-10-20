package com.example.gestionimmobilier.Mapper;

import com.example.gestionimmobilier.Dtos.UserDetailDto;
import com.example.gestionimmobilier.Dtos.UserDto;
import com.example.gestionimmobilier.Entity.User;

public interface IUserMapper {



    UserDto fromUser(User user);
    User fromUserDTO(UserDto userDto);


    UserDetailDto fromUserDetail(User user);
}
