package org.itstep.usersms.services;

import org.itstep.usersms.models.DTOs.AddRoleToUserDto;
import org.itstep.usersms.models.DTOs.UserDto;
import org.itstep.usersms.models.DTOs.UserUpdateDto;
import org.itstep.usersms.models.entities.User;
import org.itstep.usersms.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UsersService {

    UserDto getUserById(Long id);

    User getUserByEmail(String email);

    List<UserDto> getAllUsers();

    UserDto updateUser(UserUpdateDto user);

    Boolean deleteUser(Long id);

    UserDto addUser(User user);

    CustomUserDetails getUserDetails(String email);

    UserDto byJwtToken(String token);

    UserDto addUserRole(AddRoleToUserDto addRoleToUserDto);
}
