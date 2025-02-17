package com.app.reto.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.RenderingHints.Key;
import java.util.Date;

@Service
public class JwtService {

	@Value("${jwt.secret.key}")
    private String secretKey;
	
	public static final long EXPIRE_DURATION = 5 * 60 * 60 * 1000; //  5 HORAS
	
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION)) 
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractUsername(String token) {
        return validateToken(token).getSubject();
    }
}