package org.itstep.usersms.controllers;

import lombok.extern.slf4j.Slf4j;
import org.itstep.usersms.controllers.responses.TokenBody;
import org.itstep.usersms.models.DTOs.UserLoginDto;
import org.itstep.usersms.models.DTOs.UserRegisterDto;
import org.itstep.usersms.models.entities.Role;
import org.itstep.usersms.models.entities.User;
import org.itstep.usersms.repos.RolesRepository;
import org.itstep.usersms.security.JwtTokenService;
import org.itstep.usersms.services.UsersService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@Slf4j
public class AuthController {

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RolesRepository rolesRepository;

    @PostMapping("api/v1/login")
    public TokenBody login(@RequestBody UserLoginDto userLoginDto) {
        User userFromDb = usersService.getUserByEmail(userLoginDto.getEmail());

        if (Objects.nonNull(userFromDb) && userFromDb.getPassword().equals(userLoginDto.getPassword())) {
            return new TokenBody(tokenService.generateJwtToken(userFromDb.getEmail(), userFromDb.getUserId()));
        }

        throw new UsernameNotFoundException("Incorrect email or password");
    }

    @PostMapping("api/v1/register")
    public TokenBody register(@RequestBody UserRegisterDto userRegisterDto) {

        User newUser = modelMapper.map(userRegisterDto, User.class);

        List<Role> roles = new ArrayList<>();
        roles.add(rolesRepository.getById(2l));
        newUser.setRoles(roles);

        usersService.addUser(newUser);

        return new TokenBody(tokenService.generateJwtToken(newUser.getEmail(), newUser.getUserId()));
    }
}
