package org.itstep.usersms.security;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import io.jsonwebtoken.*;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtTokenServiceImpl implements JwtTokenService {


    @Value("${secretkey}")
    String secretkey;


    public String generateJwtToken(String email, Long id) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        return Jwts.builder()
                .setId(id.toString())
                .setSubject(email)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, this.secretkey)
                .compact();
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secretkey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getEmailFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secretkey).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getIdFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(this.secretkey).parseClaimsJws(token).getBody();
        log.warn(claims.getId());
        return claims.getId();
    }

}
