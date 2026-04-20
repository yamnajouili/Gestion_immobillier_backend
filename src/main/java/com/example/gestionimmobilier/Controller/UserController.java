package com.example.gestionimmobilier.Controller;


import com.example.gestionimmobilier.Dtos.UserDto;
import com.example.gestionimmobilier.Service.IUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Users", description = "CRUD Users")

@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserService iUserService;


    @GetMapping("/current-user")
    public UserDto getCurrentUser() {
        return iUserService.getLoggedInUser();
    }

}
