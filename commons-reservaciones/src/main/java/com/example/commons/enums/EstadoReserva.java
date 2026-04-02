package com.example.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EstadoReserva {

	CONFIRMADA(1L, "Reserva creada"),
	EN_CURSO(2L, "Check-in realizado"),
	FINALIZADA(3L, "Check-out realizado"),
	CANCELADA(4L, "Reserva cancelada");
	
	private final Long codigo;
	
	private final String descripcion;
	
	public static EstadoReserva fromCodigo(Long codigo) {
        for (EstadoReserva estado : EstadoReserva.values()) {
            if (estado.getCodigo() == codigo) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de estado no válido: " + codigo);
    }
	
	public static EstadoReserva fromDescripcion(String descripcion) {
        for (EstadoReserva estado : EstadoReserva.values()) {
            if (estado.getDescripcion() == descripcion) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Descripción de estado no válida: " + descripcion);
    }

	
}
