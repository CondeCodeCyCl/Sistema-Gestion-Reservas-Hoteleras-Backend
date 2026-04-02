package com.example.commons.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum EstadoHabitacion {
    DISPONIBLE("1", "Disponible para uso"),
    OCUPADA("2", "Habitación ocupada"),
    LIMPIEZA("3", "En proceso de limpieza"),
    MANTENIMIENTO("4", "En mantenimiento");


    private final String codigo;
    private final String descripcion;

    public static EstadoHabitacion fromCodigo(String codigo) {
        if (codigo == null) return DISPONIBLE; 
        for (EstadoHabitacion e : values()) {
            if (e.codigo.equals(codigo)) {
                return e;
            }
        }
        throw new IllegalArgumentException("Código de estado no válido en BD: " + codigo);
    }

    public static EstadoHabitacion fromCodigo(Integer codigo) {
        if (codigo == null) return DISPONIBLE;
        return fromCodigo(String.valueOf(codigo));
    }

}
