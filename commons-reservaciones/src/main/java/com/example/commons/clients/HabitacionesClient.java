package com.example.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.commons.configuration.FeignClientConfig;

@FeignClient(name = "habitaciones-msv", configuration = FeignClientConfig.class)
public interface HabitacionesClient {

	@GetMapping("/{id}")
	HabitacionResponse obtenerHabitacionPorId(@PathVariable Long id);
	
}
