package com.reddit.post.security;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Slf4j
public class JwtTokenUtil implements Serializable {

    private static final long serialVersionUID = -2550185165626007488L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt-secret-key}")
    private String secret;

    public String getUsernameByJwt(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.get("username").toString();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        }catch (SignatureException e) {
            log.error(e.getMessage());
        }catch (MalformedJwtException e) {
            log.error(e.getMessage());
        }catch (ExpiredJwtException e) {
            log.error(e.getMessage());
        }catch (UnsupportedJwtException e) {
            log.error(e.getMessage());
        }catch (IllegalArgumentException e){
            log.error(e.getMessage());
        }
        return false;
    }

}
