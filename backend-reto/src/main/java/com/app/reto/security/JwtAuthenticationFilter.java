package com.app.reto.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse; 
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException { 
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
        	
            token = token.substring(7); 
            System.out.println("Token recibido: "+ token);
            try {
                Claims claims = jwtService.validateToken(token);
                String username = claims.getSubject();

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    SecurityContextHolder.getContext().setAuthentication(
                            new UsernamePasswordAuthenticationToken(username, null, null)
                    );
                }
            } catch (Exception e) {
                logger.error("Token JWT no válido");
            }
        }else {
        	System.out.println("No se envia token");
        }

        filterChain.doFilter(request, response);  
    }
}
