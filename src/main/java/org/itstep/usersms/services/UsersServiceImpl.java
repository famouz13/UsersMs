package org.itstep.usersms.services;

import org.itstep.usersms.models.DTOs.AddRoleToUserDto;
import org.itstep.usersms.models.DTOs.UserDto;
import org.itstep.usersms.models.DTOs.UserUpdateDto;
import org.itstep.usersms.models.entities.Role;
import org.itstep.usersms.models.entities.User;
import org.itstep.usersms.repos.RolesRepository;
import org.itstep.usersms.repos.UsersRepository;
import org.itstep.usersms.security.CustomUserDetails;
import org.itstep.usersms.security.JwtTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsersServiceImpl implements UsersService {

    private final ModelMapper modelMapper;

    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    private final JwtTokenService tokenService;

    public UsersServiceImpl(UsersRepository usersRepository, ModelMapper modelMapper, JwtTokenService tokenService, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.modelMapper = modelMapper;
        this.tokenService = tokenService;
        this.rolesRepository = rolesRepository;
    }

    @Override
    public UserDto getUserById(Long id) {
        User user = usersRepository.getById(id);
        if (Objects.nonNull(user))
            return modelMapper.map(user, UserDto.class);
        return new UserDto();
    }

    @Override
    public User getUserByEmail(String email) {
        User user = usersRepository.findByEmail(email);
        return user;
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = usersRepository.findAll();
        return users.stream().map(n -> modelMapper.map(n, UserDto.class)).collect(Collectors.toList());

    }

    @Override
    public UserDto updateUser(UserUpdateDto updateDto) {
        try {
            User userFromDb = usersRepository.findById(updateDto.getId()).get();

            if (Objects.nonNull(updateDto.getFirstName())) {
                userFromDb.setFirstName(updateDto.getFirstName());
            }

            if (Objects.nonNull(updateDto.getSecondName())) {
                userFromDb.setSecondName(updateDto.getSecondName());
            }

            if (Objects.nonNull(updateDto.getEmail())) {
                userFromDb.setEmail(updateDto.getEmail());
            }

            usersRepository.save(userFromDb);
            return modelMapper.map(userFromDb, UserDto.class);
        } catch (Exception e) {
            return new UserDto();
        }
    }

    @Override
    public Boolean deleteUser(Long id) {
        try {
            usersRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public UserDto addUser(User user) {
        try {
            usersRepository.save(user);
            return modelMapper.map(user, UserDto.class);
        } catch (Exception e) {
            return new UserDto();
        }
    }

    @Override
    public CustomUserDetails getUserDetails(String s) throws UsernameNotFoundException {
        return CustomUserDetails.fromUserToCustomUserDetails(this.getUserByEmail(s));
    }

    @Override
    public UserDto byJwtToken(String token) {
        Long id = Long.valueOf(tokenService.getIdFromToken(token));
        User user = usersRepository.getById(id);

        if (Objects.nonNull(user)) {
            return modelMapper.map(user, UserDto.class);
        }

        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserDto addUserRole(AddRoleToUserDto addRoleToUserDto) {
        User userFromDb = usersRepository.findById(addRoleToUserDto.getUserId()).get();
        Role roleFromDb = rolesRepository.findById(addRoleToUserDto.getRoleId()).get();

        if (Objects.nonNull(userFromDb) && Objects.nonNull(roleFromDb)) {
            List<Role> roles = userFromDb.getRoles();
            roles.add(roleFromDb);
            usersRepository.save(userFromDb);
            return modelMapper.map(userFromDb, UserDto.class);
        }

        return new UserDto();
    }
}
