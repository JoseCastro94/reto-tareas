package com.app.reto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.reto.model.Tarea;
import com.app.reto.response.GenericApiResponse;
import com.app.reto.service.TareaService;

import java.util.List;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin
public class TareaController {
	@Autowired
	private TareaService tareaService;
	
	@GetMapping
    public ResponseEntity<List<Tarea>> listar() {
        List<Tarea> tareas = tareaService.listar();
        return ResponseEntity.ok(tareas);
    }

	@PostMapping
    public ResponseEntity<?> crear(@RequestBody @Validated Tarea request) {
        Tarea tareaCreada = tareaService.crear(request);
        
        GenericApiResponse<Tarea> response = new GenericApiResponse(
                HttpStatus.CREATED.value(),  
                tareaCreada,                
                "Tarea registrada correctamente" 
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
	
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Integer id) {
    	Tarea tarea = tareaService.buscarPorId(id);
        return ResponseEntity.ok(new GenericApiResponse(HttpStatus.OK.value(), tarea, "Tarea encontrada"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id, @RequestBody @Validated Tarea request) {
        Tarea tareaActualizada = tareaService.actualizar(id, request);
        
        GenericApiResponse<Tarea> response = new GenericApiResponse(
                HttpStatus.OK.value(),
                tareaActualizada,
                "Tarea actualizada correctamente"
        );
        
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        tareaService.eliminar(id);

        GenericApiResponse<String> response = new GenericApiResponse<>(
                HttpStatus.OK.value(),
                null,
                "Tarea eliminada correctamente"
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
