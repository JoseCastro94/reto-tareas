package com.app.reto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.app.reto.exceptions.ResourceNotFoundException;
import com.app.reto.model.Tarea;
import com.app.reto.repository.ITareaRepository;

import jakarta.transaction.Transactional;

@Service
public class TareaService {
	
	@Autowired
    private ITareaRepository tareaRepository;

	@Transactional
	public Tarea crear(Tarea tarea) {
	    return tareaRepository.save(tarea);
	}
	
    public List<Tarea> listar() {
	  return tareaRepository.findAll();
    }
    
    public Tarea buscarPorId(Integer id) {
    	Tarea obj = tareaRepository.findById(id).orElse(null);
        if (obj == null) {
            throw new ResourceNotFoundException("No se encontro tarea con ID " + id);
        }
        return obj;
    }
    
    @Transactional
    public Tarea actualizar(Integer id, Tarea request) {
        Tarea tareaExistente = tareaRepository.findById(id).orElse(null);
        if (tareaExistente == null) {
            throw new ResourceNotFoundException("No se encontró tarea con ID " + id);
        }
        
        tareaExistente.setTitulo(request.getTitulo());
        tareaExistente.setDescripcion(request.getDescripcion());
        tareaExistente.setEstado(request.getEstado());
        return tareaRepository.save(tareaExistente);
    }

    @Transactional
    public void eliminar(Integer id) {
        Tarea tarea = tareaRepository.findById(id).orElse(null);
        if (tarea == null) {
            throw new ResourceNotFoundException("No se encontró tarea con ID " + id);
        }
        tareaRepository.delete(tarea);
    }

}
