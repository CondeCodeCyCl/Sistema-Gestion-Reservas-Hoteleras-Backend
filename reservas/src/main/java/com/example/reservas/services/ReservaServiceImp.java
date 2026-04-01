package com.example.reservas.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.commons.enums.EstadoRegistro;
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

	@Override
	public List<ReservaResponse> listar() {
		
		return reservaRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO)
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
		
		reservaRepository.save(reserva);
		
		return reservasMapper.entityToResponse(reserva);
	}

	@Override
	public ReservaResponse actualizar(ReservaRequest request, Long id) {
		
		Reservas reserva = obtenerReservaOException(id);
		
		reservasMapper.updateEntityFromRequest(request, reserva);
		
		return reservasMapper.entityToResponse(reserva);
	}

	@Override
	public void eliminar(Long id) {
		Reservas reserva = obtenerReservaOException(id);
		reserva.setEstadoRegistro(EstadoRegistro.ELIMINADO);
		
	}
	
	private Reservas obtenerReservaOException(Long id) {
		return reservaRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO).orElseThrow(() ->
		 new RecursoNoEncontradoException("No se encontró una reserva activa con id: "));
	}
	
	

}
