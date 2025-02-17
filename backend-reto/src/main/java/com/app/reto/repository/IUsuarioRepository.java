package com.app.reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.reto.model.Usuario;

@Repository
public interface IUsuarioRepository extends JpaRepository<Usuario, Integer>{
	 public Usuario findByUsername(String username); 
	 
	 boolean existsByUsername(String username);
}
