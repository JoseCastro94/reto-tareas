package com.app.reto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.app.reto.dto.UsuarioDTO;
import com.app.reto.exceptions.CustomBadCredentialsException;
import com.app.reto.model.Usuario;
import com.app.reto.security.JwtRequest;
import com.app.reto.security.JwtResponse;
import com.app.reto.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {
	@Autowired
    private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody JwtRequest request) {
	    try {
	        String jwtToken = authService.login(request);
	        return ResponseEntity.ok(new JwtResponse(jwtToken));
	    } catch (BadCredentialsException e) { 
	        throw new CustomBadCredentialsException("Credenciales incorrectas.");
	    }
	}
    
    @PostMapping("/registro")
    public ResponseEntity<Usuario> registro(@RequestBody UsuarioDTO request) {
        Usuario usuario = authService.registro(request);
        return ResponseEntity.ok(usuario);
    }
}
