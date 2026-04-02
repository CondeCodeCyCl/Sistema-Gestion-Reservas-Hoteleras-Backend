package com.example.commons.dto;

import com.example.commons.enums.EstadoHabitacion;

public record HabitacionResponse(
		
		Long id,
		Integer numero,
		String tipo,
		Double precio,
		Short capacidad,
		EstadoHabitacion estadoHabitacion

) {
}
