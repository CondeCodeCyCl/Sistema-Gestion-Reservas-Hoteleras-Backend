package com.example.commons.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HabitacionRequest(
		
		@NotNull
		@Min(value = 1, message = "El número debe ser mayor a 0")
		Integer numero,
		
		@NotBlank(message = "El tipo es requerido")
		String tipo,
		
		@NotNull(message = "El precio es requerido")
	    @DecimalMin(value = "0", message = "El precio debe ser mayor a 0")
		Double precio,
		
		@NotNull(message = "La capacidad es requerida")
		@Min(value = 1, message = "La capacidad debe ser mayor a 0")
		Short capacidad
		
) {}
