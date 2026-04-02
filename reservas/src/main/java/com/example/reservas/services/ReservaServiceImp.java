package com.example.reservas.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.commons.clients.HabitacionesClient;
import com.example.commons.clients.HuespedesClient;
import com.example.commons.dto.HabitacionResponse;
import com.example.commons.dto.HuespedResponse;
import com.example.commons.enums.EstadoRegistro;
import com.example.commons.enums.EstadoReserva;
import com.example.commons.exceptions.RecursoNoEncontradoException;
import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.dto.ReservaResponse;
import com.example.reservas.entities.Reservas;
import com.example.reservas.mappers.ReservasMapper;
import com.example.reservas.repositories.ReservaRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ReservaServiceImp implements ReservaService{
	
	private final ReservaRepository reservaRepository;
	
	private final ReservasMapper reservasMapper;
	
	private final HuespedesClient huespedesClient;
	
	private final HabitacionesClient habitacionesClient;

	@Override
	public List<ReservaResponse> listar() {
		
		return reservaRepository.findAll()
				.stream().map(reservasMapper::entityToResponse)
				.toList();
	}

	@Override
	public ReservaResponse obtenerPorId(Long id) {
		return reservasMapper.entityToResponse(obtenerReservaOException(id));
	}

	@Override
	public ReservaResponse registrar(ReservaRequest request) {
		
		Reservas reserva = reservasMapper.requestToEntity(request);
		HuespedResponse huesped = obtenerHuespedOException(request.idHuesped());
		HabitacionResponse habitacion = obtenerHabitacionOException(request.idHabitacion());
		
		habitacionesClient.actualizarDisp(request.idHabitacion(), 2L);
		
		return reservasMapper.entityToResponse(reservaRepository.save(reserva), huesped, habitacion);
	}

	@Override
	public ReservaResponse actualizar(ReservaRequest request, Long id) {
		
		Reservas reserva = obtenerReservaOException(id);
		
		EstadoReserva actual = EstadoReserva.fromDescripcion(reserva.getEstadoReserva().getDescripcion());
		
		if (actual==EstadoReserva.FINALIZADA ||
			actual==EstadoReserva.CANCELADA) {
			
			throw new IllegalArgumentException("Las reservas finalizadas o canceladas no se pueden modifcar.");
		}
		
		if (actual==EstadoReserva.EN_CURSO &&
			comprobarCongruenciaFechas(reserva.getFechaEntrada(), request.fechaSalida())) {
			
			reserva.setFechaSalida(request.fechaSalida());
		}else if (actual==EstadoReserva.CONFIRMADA &&
			comprobarCongruenciaFechas(request.fechaEntrada(), request.fechaSalida())) {
			
			reserva.setIdHabitacion(request.idHabitacion());
			reserva.setIdHuesped(request.idHuesped());
			reserva.setMontoTotal(request.montoTotal());
			reserva.setFechaEntrada(request.fechaEntrada());
			reserva.setFechaSalida(request.fechaSalida());
		}else {
			throw new IllegalArgumentException(actual.getDescripcion()+": La fecha de salida " + request.fechaSalida() +
					" debe ser después de la fecha de la fecha de entrada. " + request.fechaEntrada());
		}
		
		return reservasMapper.entityToResponse(reserva);
	}

	@Override
	public void eliminar(Long id) {
		Reservas reserva = obtenerReservaOException(id);
		reserva.setEstadoRegistro(EstadoRegistro.ELIMINADO);
		
	}
	
	@Override
	public ReservaResponse checkIn(Long id) {
		
		Reservas reserva = obtenerReservaOException(id);
		EstadoReserva nuevo = EstadoReserva.fromCodigo(2L);
		
		transicionReservaValida(reserva.getEstadoReserva(), nuevo);
		
		reserva.setEstadoReserva(EstadoReserva.EN_CURSO);
		
		return reservasMapper.entityToResponse(reserva);
	}
	
	@Override
	public ReservaResponse checkOut(Long id) {
		Reservas reserva = obtenerReservaOException(id);
		EstadoReserva nuevo = EstadoReserva.fromCodigo(3L);
		
		transicionReservaValida(reserva.getEstadoReserva(), nuevo);
		
		reserva.setEstadoReserva(EstadoReserva.EN_CURSO);
		
		return reservasMapper.entityToResponse(reserva);
	}
	
	@Override
	public boolean cancelar(Long id) {
		Reservas reserva = obtenerReservaOException(id);
		EstadoReserva nuevo = EstadoReserva.fromCodigo(4L);
		
		transicionReservaValida(reserva.getEstadoReserva(), nuevo);
		
		reserva.setEstadoReserva(EstadoReserva.CANCELADA);
		
		habitacionesClient.actualizarDisp(reserva.getIdHabitacion(), 1L);
		
		return true;
	}
	
	private Reservas obtenerReservaOException(Long id) {
		return reservaRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(() ->
		 new RecursoNoEncontradoException("No se encontró una reserva activa con id: "));
	}
	
	private HuespedResponse obtenerHuespedOException(Long id) {
		return huespedesClient.obtenerHuespedPorId(id);
	}
	
	private HabitacionResponse obtenerHabitacionOException(Long id) {
		return habitacionesClient.obtenerHabitacionConDisponibilidad(id);
	}

	private boolean transicionReservaValida(EstadoReserva actual, EstadoReserva nuevo) {
		
		switch (actual) {
		case CONFIRMADA:
				if (nuevo.equals(EstadoReserva.EN_CURSO)) {
					return true;
				}else if(nuevo.equals(EstadoReserva.CANCELADA)) {
					return true;
				}else {
					throw new IllegalStateException("Las reservas confirmadas solo pueden pasar a canceladas o en curso.");
				}
			
		case EN_CURSO:
			if (nuevo.equals(EstadoReserva.FINALIZADA)) {
				return true;
			}else {
				throw new IllegalStateException("Las reservas en curso solo pueden pasar a finalizadas.");
			}
		
		default:
				throw new IllegalStateException("El estado no es válido o la reserva está finalizada (o fue cancelada)");
		}
		
	}
	
	private boolean comprobarCongruenciaFechas(LocalDateTime fechaEntrada, LocalDateTime fechaSalida) {
		return fechaSalida.isAfter(fechaEntrada);
	}

}
