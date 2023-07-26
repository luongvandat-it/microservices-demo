package vn.edu.iuh.gatewayservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.edu.iuh.gatewayservice.entities.value_objects.UserVO;

import java.security.Key;
import java.util.Date;
import java.util.Set;

@Component
@Slf4j
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    private Key key;

    /**
     * call method after JwtUtil created
     */
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    /**
     * Get all claims from JSON Web Token (JWT)
     * @param token JSON Web Token (JWT)
     * @return all claims
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    /**
     * Check the validity of token
     * @param token JSON Web Token (JWT)
     * @return true of valid (and vice versa)
     */
    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

    public String getUser(String token) {
        Claims claims = this.getAllClaimsFromToken(token);
        return claims.get("roles", String.class);
    }
}
