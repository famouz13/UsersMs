package org.itstep.usersms.models.DTOs;


import lombok.Data;
import org.itstep.usersms.models.entities.Role;

import java.util.List;

@Data
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String secondName;

    private List<Role> roles;
}

