package com.example.reservas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.commons.dto.DatosHabitacion;
import com.example.commons.dto.DatosHuesped;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ReservaResponse(
		
		Long id,
		DatosHuesped huesped,
		DatosHabitacion habitacion,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime fechaEntrada,
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm")
		LocalDateTime fechaSalida,
		BigDecimal montoTotal,
		String estadoReserva
		
) {
}
