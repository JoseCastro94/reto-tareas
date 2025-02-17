package com.app.reto.service;

import com.app.reto.dto.UsuarioDTO;
import com.app.reto.exceptions.ResourceNotFoundException;
import com.app.reto.model.Rol;
import com.app.reto.model.Usuario;
import com.app.reto.repository.IRolRepository;
import com.app.reto.repository.IUsuarioRepository;
import com.app.reto.security.JwtRequest;
import com.app.reto.security.JwtService;

import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioDetailsService usuarioDetailsService;
    
    @Autowired
    private IUsuarioRepository usuarioRepository;

    @Autowired
    private IRolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String login(JwtRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return jwtService.generateToken(userDetails.getUsername());
    }
    
    public Usuario registro(UsuarioDTO request) {
    	if (usuarioRepository.existsByUsername(request.getUsername())) {
            throw new ResourceNotFoundException("El usuario "+request.getUsername()+" ya está registrado");
        }
    	
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        Usuario usuario = new Usuario();
        usuario.setUsername(request.getUsername());
        usuario.setPassword(encodedPassword);
        usuario.setCorreo(request.getCorreo());
        usuario.setFechaCreacion(new Date());

        Rol rol = rolRepository.findByNombre("ROLE_ADMIN");
        
        if(rol == null) {
        	rol = rolRepository.save(new Rol("ROLE_ADMIN"));
        	// throw new ResourceNotFoundException("Rol no encontrado");
        }
        
        usuario.setRoles(Collections.singletonList(rol));

        return usuarioRepository.save(usuario);
    }
}
