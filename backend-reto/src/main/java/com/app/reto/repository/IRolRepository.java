package com.app.reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.reto.model.Rol;

@Repository
public interface IRolRepository extends JpaRepository<Rol, Integer> {
	public Rol findByNombre(String nombre);
}
