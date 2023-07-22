package vn.edu.iuh.authservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import vn.edu.iuh.authservice.entities.AuthRequest;
import vn.edu.iuh.authservice.entities.AuthResponse;
import vn.edu.iuh.authservice.entities.value_objects.UserVO;
import vn.edu.iuh.authservice.utils.JwtType;
import vn.edu.iuh.authservice.utils.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final RestTemplate restTemplate;
    private final JwtUtil jwtUtil;


    public AuthResponse register(AuthRequest request) {
        request.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        UserVO userVO = restTemplate.postForObject("http://user-service/api/account/register", request, UserVO.class);
        // FIXME fix return save user
        Assert.notNull(userVO, "Failed to register user. Please try again later!!");
        String accessToken = jwtUtil.generateToken(userVO, JwtType.JWT_ACCESS);
        // Refresh Token will storage database
        String refreshToken = jwtUtil.generateToken(userVO, JwtType.JWT_REFRESH);
        return new AuthResponse(accessToken, refreshToken);
    }
}
