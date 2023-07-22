package vn.edu.iuh.authservice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.edu.iuh.authservice.entities.value_objects.UserVO;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expirationTime;

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
     * Get expiration date from Json Web Token (JWT)
     * @param token String
     * @return Expiration Date
     */
    public Date getExpirationDateFromToken(String token) {
        return this.getAllClaimsFromToken(token).getExpiration();
    }

    /**
     * Check the validity of token
     * @param token JSON Web Token (JWT)
     * @return true of valid (and vice versa)
     */
    private boolean isTokenExpired(String token) {
        return this.getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    public String generateToken(UserVO userVO, JwtType type) {
        Map<String, Object> claims = new HashMap<>();
//        claims.put("role", userVO.getRole());
        claims.put("id", userVO.getId());
        return this.doGenerateToken(claims, userVO.getPhone(), type);
    }

    public String doGenerateToken(Map<String, Object> claims, String phone, JwtType type) {
        final Date createdDate = new Date();
        final Date  expirationDate = new Date(createdDate.getTime() + expirationTime);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(phone)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();
    }

    public boolean isInvalid(String token) {
        return this.isTokenExpired(token);
    }

}
