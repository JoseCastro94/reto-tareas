package com.app.reto.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.app.reto.model.Usuario;
import com.app.reto.repository.IUsuarioRepository;

@Service
public class UsuarioDetailsService implements UserDetailsService {
	@Autowired
	private IUsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioRepository.findByUsername(username);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuario no encontrado: " + username);
		}

		List<GrantedAuthority> authorities = usuario.getRoles().stream()
				.map(rol -> new SimpleGrantedAuthority(rol.getNombre())).collect(Collectors.toList());

		return new User(usuario.getUsername(), usuario.getPassword(), authorities);
	}
}
