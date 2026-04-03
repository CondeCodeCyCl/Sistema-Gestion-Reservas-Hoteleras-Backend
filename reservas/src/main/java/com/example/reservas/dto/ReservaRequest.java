package com.example.reservas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ReservaRequest(
		
		@NotNull(message = "El id del huesped no debe ser nulo")
		@Positive(message = "El id de huesped debe ser positivo")
		Long idHuesped,
		
		@NotNull(message = "El id del habitacion no debe ser nulo")
		@Positive(message = "El id habitacion debe ser positivo")
		Long idHabitacion,
		
		@NotNull(message = "La fecha de entrada es requerida")
		@FutureOrPresent(message = "La fecha de entrada debe ser futura")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
		LocalDateTime fechaEntrada,
		
		@NotNull(message = "La fecha de salida es requerida")
		@FutureOrPresent(message = "La fecha de salida debe ser futura")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
		LocalDateTime fechaSalida,
		
		@NotNull(message = "El monto total es requerido")
		@Positive(message = "El id habitacion debe ser positivo")
		BigDecimal montoTotal,
		
		@Positive(message = "El id del estado de la reserva debe ser positivo")
		Long idEstadoReserva,
		
		@Positive(message = "El id del estado de registro debe ser positivo")
		Long idEstadoRegistro
		
) {

}
