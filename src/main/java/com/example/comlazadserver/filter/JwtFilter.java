package com.example.comlazadserver.filter;

import com.example.comlazadserver.dto.AuthenRequest;
import com.example.comlazadserver.entity.User;
import com.example.comlazadserver.repository.UserRepository;
import com.example.comlazadserver.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Service
public class JwtFilter extends OncePerRequestFilter {
    private RequestMatcher matcher = new AntPathRequestMatcher("/api/v1/authen/**");

    @Autowired
    JwtService jwtService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        String jwtToken = "";
        if (!bearerToken.isEmpty() && bearerToken != null && bearerToken.startsWith("Bearer")) {
            jwtToken = bearerToken.substring(7);
        }
        boolean isTokenVerified = jwtService.verifyToken(jwtToken);

        if (isTokenVerified) {
            String username = jwtService.getUsername(jwtToken);
            User user = userRepository.findByUsername(username);
            SecurityContextHolder.getContext().setAuthentication(UsernamePasswordAuthenticationToken.authenticated(user, null, null));
        }
        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return matcher.matches(request);
    }

}
