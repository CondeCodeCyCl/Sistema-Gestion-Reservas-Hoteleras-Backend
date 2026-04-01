package com.example.reservas.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.commons.enums.EstadoRegistro;
import com.example.commons.enums.EstadoReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "RESERVAS")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Reservas {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_RESERVA")
	private Long id;
	
	@Column(name = "ID_HUESPED", nullable = false)
	private Long idHuesped;
	
	@Column(name = "ID_HABITACION", nullable = false)
	private Long idHabitacion;
	
	@Column(name = "FECHA_ENTRADA", nullable = false)
	private LocalDateTime fechaEntrada;
	
	@Column(name = "FECHA_SALIDA", nullable = false)
	private LocalDateTime fechaSalida;
	
	@Column(name = "MONTO_TOTAL", nullable = false)
	private BigDecimal montoTotal;
	
	@Column(name = "ESTADO_RESERVA", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoReserva estadoReserva;
	
	@Column(name = "ESTADO_REGISTRO", nullable = false)
	@Enumerated(EnumType.STRING)
	private EstadoRegistro estadoRegistro;

}
