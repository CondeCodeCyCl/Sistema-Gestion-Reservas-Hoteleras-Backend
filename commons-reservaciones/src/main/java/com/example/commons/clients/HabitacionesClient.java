package com.example.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.commons.configuration.FeignClientConfig;
import com.example.commons.dto.HabitacionResponse;

import jakarta.validation.constraints.Positive;

@FeignClient(name = "habitaciones-msv", configuration = FeignClientConfig.class)
public interface HabitacionesClient {

	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);
	
	@GetMapping("/id-habitacion-disp/{id}")
	HabitacionResponse obtenerHabitacionConDisponibilidad(@PathVariable Long id);
	
	@PutMapping("/{idHab}/disponibilidad/{idDisponibilidad}")
	HabitacionResponse actualizarDisp(
			@PathVariable
			@Positive(message = "El id debe ser positivo") Long idHab,
			@PathVariable
			@Positive(message = "El id estado debe ser positivo") Long idDisponibilidad);
	
}
