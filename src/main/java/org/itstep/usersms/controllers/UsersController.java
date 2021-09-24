package org.itstep.usersms.controllers;

import org.itstep.usersms.controllers.responses.TokenBody;
import org.itstep.usersms.models.DTOs.AddRoleToUserDto;
import org.itstep.usersms.models.DTOs.UserDto;
import org.itstep.usersms.models.DTOs.UserUpdateDto;
import org.itstep.usersms.models.entities.User;
import org.itstep.usersms.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("api/v1/admin/users")
    public List<UserDto> getUsers() {
        return usersService.getAllUsers();
    }

    @GetMapping("api/v1/admin/users/{id}")
    public UserDto getUsers(@PathVariable(name = "id") Long id) {
        return usersService.getUserById(id);
    }

    @PostMapping("api/v1/admin/users/jwt")
    public UserDto getByJwtToken(@RequestBody TokenBody tokenBody) {
        return usersService.byJwtToken(tokenBody.getToken());
    }

    @PostMapping("api/v1/admin/users-roles")
    public UserDto addRoleToUser(@RequestBody AddRoleToUserDto addRoleToUserDto) {
        return usersService.addUserRole(addRoleToUserDto);
    }

    @DeleteMapping("api/v1/admin/users/{id}")
    public Boolean deleteUser(@PathVariable(name = "id") Long id) {
        return usersService.deleteUser(id);
    }

    @PutMapping("api/v1/admin/users")
    public UserDto deleteUser(@RequestBody UserUpdateDto userUpdateDto) {
        return usersService.updateUser(userUpdateDto);
    }
}
