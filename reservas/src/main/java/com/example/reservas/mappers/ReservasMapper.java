package com.example.reservas.mappers;

import org.springframework.stereotype.Component;

import com.example.commons.dto.DatosHabitacion;
import com.example.commons.dto.DatosHuesped;
import com.example.commons.dto.HabitacionResponse;
import com.example.commons.dto.HuespedResponse;
import com.example.reservas.dto.ReservaRequest;
import com.example.commons.enums.EstadoRegistro;
import com.example.commons.enums.EstadoReserva;
import com.example.commons.mappers.CommonMapper;
import com.example.reservas.dto.ReservaResponse;
import com.example.reservas.entities.Reservas;

@Component
public class ReservasMapper implements CommonMapper<ReservaRequest, ReservaResponse, Reservas>{

	@Override
	public Reservas requestToEntity(ReservaRequest request) {
		if(request == null) return null;
		
		return Reservas.builder()
                .idHuesped(request.idHuesped())
                .idHabitacion(request.idHabitacion())
                .fechaEntrada(request.fechaEntrada())
                .fechaSalida(request.fechaSalida())
                .montoTotal(request.montoTotal())
                .estadoReserva(EstadoReserva.CONFIRMADA)
                .estadoRegistro(EstadoRegistro.ACTIVO)
                .build();
	}

	@Override
	public ReservaResponse entityToResponse(Reservas entity) {
		if(entity == null) return null;
		
		return new ReservaResponse(
				entity.getId(),
				null,
				null,
				entity.getFechaEntrada(),
				entity.getFechaSalida(),
				entity.getMontoTotal(),
				entity.getEstadoReserva().getDescripcion());
	}
	
	public ReservaResponse entityToResponse(Reservas entity, HuespedResponse huesped, HabitacionResponse habitacion) {
		if(entity == null) return null;
		
		return new ReservaResponse(
				entity.getId(),
				huespedResponseToDatoshuesped(huesped),
				habitacionResponseToDatoshabitacion(habitacion),
				entity.getFechaEntrada(),
				entity.getFechaSalida(),
				entity.getMontoTotal(),
				entity.getEstadoReserva().getDescripcion());
	}
	
	

	@Override
	public Reservas updateEntityFromRequest(ReservaRequest request, Reservas entity) {
		if (request == null || entity == null) return null;

		entity.setIdHuesped(request.idHuesped());
		entity.setIdHabitacion(request.idHabitacion());
		entity.setFechaEntrada(request.fechaEntrada());
		entity.setFechaSalida(request.fechaSalida());
		entity.setMontoTotal(request.montoTotal());
		
		return entity;
	}
	
	public Reservas updateEntityFromRequest(ReservaRequest request, Reservas entity, EstadoReserva estadoReserva) {
		if (request == null || entity == null) return null;

		updateEntityFromRequest(request, entity);
		entity.setEstadoReserva(estadoReserva);
		
		return entity;
	}
	
	private DatosHuesped huespedResponseToDatoshuesped(HuespedResponse huesped) {
		if(huesped == null) return null;
		
		return new DatosHuesped(
				huesped.nombre(),
				huesped.email(),
				huesped.telefono(),
				huesped.documento(),
				huesped.nacionalidad()
				);
	}
	
	private DatosHabitacion habitacionResponseToDatoshabitacion(HabitacionResponse habitacion) {
		if(habitacion == null) return null;
		
		return new DatosHabitacion(
				habitacion.numero(),
				habitacion.tipo(),
				habitacion.precio(),
				habitacion.capacidad()
				);
	}

}
