package org.itstep.usersms.models.DTOs;

import lombok.Data;

@Data
public class UserRegisterDto {

    private String email;
    private String password;
    private String firstName;
    private String secondName;

}
