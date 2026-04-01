package com.example.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoHabitacion {
	
	DISPONIBLE(1L, "Lista para asignarse"),
	OCUPADA(2L, "Asignada a una reserva"),
	LIMPIEZA(3L, "En limpieza"),
	MANTENIMIENTO(4L, "En reparación");
	
	private final Long codigo;
	
	private final String descripcion;
	
	public static EstadoHabitacion fromCodigo(Long codigo) {
        for (EstadoHabitacion estado : EstadoHabitacion.values()) {
            if (estado.getCodigo() == codigo) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Código de estado no válido: " + codigo);
    }

}
