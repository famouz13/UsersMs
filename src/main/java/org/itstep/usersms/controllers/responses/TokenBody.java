package org.itstep.usersms.controllers.responses;

import lombok.*;
import org.itstep.usersms.models.DTOs.UserDto;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class TokenBody {

    private String token;

}
