package com.example.saveinformation.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Service
public class JwtService {

    @Value("${spring.security.token.expiration}")
    private long expiration;

    @Value("${spring.security.token.secret}")
    private String secret;

    public String generateToken(String phoneNumber){
        Date from = Date.from(new Date().toInstant().plusSeconds(this.expiration));

        return  Jwts.builder()
                .setSubject(phoneNumber)
                .setIssuedAt(new Date())
                .setExpiration(from)
                .signWith(signInKey())
                .compact();
    }

    public Key signInKey(){
        return Keys
                .hmacShaKeyFor(Base64
                        .getDecoder()
                        .decode(secret));
    }

    public Claims claims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(signInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
