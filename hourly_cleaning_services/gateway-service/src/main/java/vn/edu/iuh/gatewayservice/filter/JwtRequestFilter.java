package vn.edu.iuh.gatewayservice.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.edu.iuh.gatewayservice.auth.UserPrincipal;
import vn.edu.iuh.gatewayservice.utils.JwtUtil;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;

    public JwtRequestFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");
        UserPrincipal user = null;

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Token")) {
            String jwt = authorizationHeader.substring(6);
            user = jwtUtil.getUserFromToken(jwt);
            // TODO
        }

        if (null != user) {
            Set<GrantedAuthority> authorities = new HashSet<>();

        }


        filterChain.doFilter(request, response);
    }
}
