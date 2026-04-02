package com.example.habitaciones.controllers;

import com.example.commons.controllers.CommonController;
import com.example.commons.dto.HabitacionRequest;
import com.example.commons.dto.HabitacionResponse;
import com.example.habitaciones.services.HabitacionService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class HabitacionController extends CommonController<HabitacionRequest, HabitacionResponse, HabitacionService>{


    public HabitacionController(HabitacionService service) {
		super(service);
	}

	@GetMapping
    public ResponseEntity<List<HabitacionResponse>> listar() {
        return ResponseEntity.ok(service.listar());
    }
    
    @PostMapping
    public ResponseEntity<HabitacionResponse> registrar( @RequestBody HabitacionRequest request) {
        HabitacionResponse response = service.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HabitacionResponse> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<HabitacionResponse>> obtenerDisponiblesYActivas() {
        return ResponseEntity.ok(service.obtenerDisponiblesYActivas());
    }
    
    @GetMapping("/id-habitacion/{id:[0-9]+}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionPorIdSinEstado(
            @PathVariable 
            @Positive(message = "El ID de la habitación debe ser positivo") Long id) {
            
        return ResponseEntity.ok(service.obtenerHabitacionPorIdSinEstado(id));
    }


    @PutMapping("/{id}")
    public ResponseEntity<HabitacionResponse> actualizar(
            @PathVariable Long id, 
            @RequestBody HabitacionRequest request) {
        return ResponseEntity.ok(service.actualizar(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}/disponibilidad/{idDisponibilidad}")
    public ResponseEntity<HabitacionResponse> actializarDisp(
            @PathVariable("id") 
            @Positive(message = "El ID de la habitación debe ser positivo") Long id, 
            
            @PathVariable("idDisponibilidad") 
            @Positive(message = "El ID del estado debe ser positivo") Integer idEstado) {

        HabitacionResponse response = service.cambiarEstado(id, idEstado);
        
        return ResponseEntity.ok(response);
    }
    
    
    @GetMapping("/id-habitacion-disp/{id}")
    public ResponseEntity<HabitacionResponse> obtenerHabitacionDisponiblePorId(
            @PathVariable 
            @Positive(message = "El ID de la habitación debe ser positivo") Long id) {
            
        return ResponseEntity.ok(service.obtenerHabitacionDisponiblePorId(id));
    }
}
