package com.example.gestionimmobilier.Mapper;


import com.example.gestionimmobilier.Dtos.UserDto;
import com.example.gestionimmobilier.Entity.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserMapper implements IUserMapper{
    private final ModelMapper modelMapper;
    @Override
    public UserDto fromUser(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User fromUserDTO(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
