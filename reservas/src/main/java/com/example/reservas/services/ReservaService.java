package com.example.reservas.services;

import com.example.commons.services.CrudService;
import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.dto.ReservaResponse;

public interface ReservaService extends CrudService<ReservaRequest, ReservaResponse>{
	ReservaResponse checkIn(Long id);
	ReservaResponse checkOut(Long id);
	boolean cancelar(Long id);
	ReservaResponse obtenerReservaSinEstado(Long id);
	boolean tieneReservasEnCurso(Long idHuesped);
}
