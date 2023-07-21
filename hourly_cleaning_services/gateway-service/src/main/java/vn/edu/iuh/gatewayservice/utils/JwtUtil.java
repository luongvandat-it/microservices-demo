package vn.edu.iuh.gatewayservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Component;
import vn.edu.iuh.gatewayservice.auth.UserPrincipal;

import java.text.ParseException;
import java.util.Date;

@Component
@Slf4j
public class JwtUtil {
    private static final String USER = "user";
    private static final String JWT_SECRET = "hey Mr Tien the secrect length must be at least 256 bits";

    /**
     * Generate JSON Web Token (JWT) with user information
     * @param user info user
     * @return jwt token
     */
    public String generateToken(UserPrincipal user) {
        String token = null;
        try {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USER, user);
            builder.expirationTime(generateExpirationDate());
            JWTClaimsSet claimsSet = builder.build();

            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claimsSet);
            JWSSigner signer = new MACSigner(JWT_SECRET.getBytes());
            signedJWT.sign(signer);

            token = signedJWT.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage());
        }
        return token;
    }

    /**
     * Expiration time of jwt token is one day
     * @return expiration date of JSON Web Token (JWT)
     */
    public Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
    }

    /**
     * Get all claims from JSON Web Token (JWT)
     * @param token JSON Web Token (JWT)
     * @return all claims
     */
    private JWTClaimsSet getClaimsFromToken(String token) {
        JWTClaimsSet claims = null;
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(JWT_SECRET.getBytes());
            if (signedJWT.verify(verifier)) {
                claims = signedJWT.getJWTClaimsSet();
            }
        } catch (ParseException | JOSEException e) {
            log.error(e.getMessage());
        }
        return claims;
    }

    /**
     * Check the validity of token
     * @param claims claims
     * @return true of valid (and vice versa)
     */
    private boolean isTokenExpired(JWTClaimsSet claims) {
        return getExpirationDateFromToken(claims).after(new Date());
    }

    /**
     * Get expiration date from claims
     * @param claims claims
     * @return expiration or current date
     */
    private Date getExpirationDateFromToken(JWTClaimsSet claims) {
        return null != claims ? claims.getExpirationTime() : new Date();
    }

    /**
     * Get user information from JSON Web Token (JWT)
     * @return user info
     */
    public UserPrincipal getUserFromToken(String token) {
        UserPrincipal user = null;
        try {
            JWTClaimsSet claims = getClaimsFromToken(token);
            if (null != claims && isTokenExpired(claims)) {
                JSONObject jsonObject = (JSONObject) claims.getClaim(USER);
                user = new ObjectMapper().readValue(jsonObject.toJSONString(), UserPrincipal.class);
            }
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return user;
    }
}
