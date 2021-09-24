package org.itstep.usersms.security;

public interface JwtTokenService {
    String generateJwtToken(String email, Long id);

    Boolean validateToken(String token);

    String getEmailFromToken(String token);

    String getIdFromToken(String token);
}
