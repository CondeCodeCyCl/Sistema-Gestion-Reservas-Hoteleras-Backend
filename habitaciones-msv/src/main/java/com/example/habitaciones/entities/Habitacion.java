package com.example.habitaciones.entities;

import com.example.commons.enums.EstadoHabitacion;
import com.example.commons.enums.EstadoRegistro;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "HABITACIONES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Habitacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_HABITACION")
    private Long id;

    @Column(name = "NUMERO", nullable = false)
    private Integer numero;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "PRECIO", nullable = false, precision = 10, scale = 2)
    private Double precio;

    @Column(name = "CAPACIDAD", nullable = false)
    private Short capacidad;


    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_HABITACION", nullable = false, length = 15)
    private EstadoHabitacion estadoHabitacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO_REGISTRO", nullable = false, length = 10)
    private EstadoRegistro estadoRegistro;
}