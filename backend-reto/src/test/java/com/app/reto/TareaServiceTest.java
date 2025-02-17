package com.app.reto;

import com.app.reto.exceptions.ResourceNotFoundException;
import com.app.reto.model.Tarea;
import com.app.reto.repository.ITareaRepository;
import com.app.reto.service.TareaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TareaServiceTest {

	@Mock
	private ITareaRepository tareaRepository;

	@InjectMocks
	private TareaService tareaService;

	private Tarea tarea;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		tarea = new Tarea();
		tarea.setId(0);
		tarea.setTitulo("Tarea 1");
		tarea.setDescripcion("Avance de la semana");
		tarea.setEstado("Por hacer");
	}

	@Test
	void testCrear() {
		when(tareaRepository.save(any(Tarea.class))).thenReturn(tarea);

		Tarea result = tareaService.crear(tarea);

		assertNotNull(result);
		assertEquals("Tarea 1", result.getTitulo());
	}

	@Test
	void testListar() {
		when(tareaRepository.findAll()).thenReturn(List.of(tarea));

		List<Tarea> tareas = tareaService.listar();

		assertNotNull(tareas);
		assertFalse(tareas.isEmpty());
		assertEquals(1, tareas.size());
		assertEquals("Tarea 1", tareas.get(0).getTitulo());
		verify(tareaRepository, times(1)).findAll();
	}

	@Test
	void testBuscarPorId() {
		when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));

		Tarea result = tareaService.buscarPorId(1);

		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals("Tarea 1", result.getTitulo());
	}

	@Test
	void testBuscarPorId_ThrowsResourceNotFoundException() {
		when(tareaRepository.findById(1)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			tareaService.buscarPorId(1);
		});
		assertEquals("No se encontro tarea con ID 1", exception.getMessage());
		verify(tareaRepository, times(1)).findById(1);
	}

	@Test
	void testActualizar() {
		Tarea tareaActualizada = new Tarea();
		tarea.setId(1);
		tarea.setTitulo("Tarea actualizada");
		tarea.setDescripcion("Avance de la semana actualizada");
		tarea.setEstado("En progreso");

		when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));
		when(tareaRepository.save(any(Tarea.class))).thenReturn(tareaActualizada);

		Tarea result = tareaService.actualizar(1, tareaActualizada);

		assertNotNull(result);
		assertEquals("Tarea actualizada", result.getTitulo());
		assertEquals("En Progreso", result.getEstado());
	}

	@Test
	void testEliminar() {
		when(tareaRepository.findById(1)).thenReturn(Optional.of(tarea));

		tareaService.eliminar(1);

		verify(tareaRepository, times(1)).findById(1);
		verify(tareaRepository, times(1)).delete(tarea);
	}

	@Test
	void testEliminar_ThrowsResourceNotFoundException() {
		when(tareaRepository.findById(1)).thenReturn(Optional.empty());

		ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
			tareaService.eliminar(1);
		});
		assertEquals("No se encontró tarea con ID 1", exception.getMessage());
		verify(tareaRepository, times(1)).findById(1);
	}

}
