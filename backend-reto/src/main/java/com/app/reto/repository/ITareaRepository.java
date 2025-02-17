package com.app.reto.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.reto.model.Tarea;

@Repository
public interface ITareaRepository extends JpaRepository<Tarea, Integer> {
}