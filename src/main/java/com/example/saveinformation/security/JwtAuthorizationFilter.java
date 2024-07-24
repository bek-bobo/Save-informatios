package com.example.saveinformation.security;

import com.example.saveinformation.user.UserRepository;
import com.example.saveinformation.user.dto.UserResponseDto;
import com.example.saveinformation.user.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearer = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (bearer == null || !bearer.startsWith("Bearer ") ){
            filterChain.doFilter(request, response);
            return;
        }

        String token = bearer.substring(7);

        Claims claims = jwtService. claims(token);
        String phoneNumber = claims.getSubject();

        User user = userRepository
                .findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new EntityNotFoundException("user with this phone number %s not found!".formatted(phoneNumber)));

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

        SecurityContextHolder
                .getContext()
                .setAuthentication(authenticationToken);

        filterChain.doFilter(request,response);
    }
}
