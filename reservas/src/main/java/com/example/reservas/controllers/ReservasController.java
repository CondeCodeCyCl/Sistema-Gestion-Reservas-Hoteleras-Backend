package com.example.reservas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.commons.controllers.CommonController;
import com.example.reservas.dto.ReservaRequest;
import com.example.reservas.dto.ReservaResponse;
import com.example.reservas.services.ReservaService;


@RestController
@Validated
public class ReservasController extends CommonController<ReservaRequest, ReservaResponse, ReservaService>{

	public ReservasController(ReservaService service) {
		super(service);
	}
	
	@GetMapping("sin-estado/{id}")
	public ResponseEntity<ReservaResponse> obtenerReservaSinEstado(@PathVariable Long id){
		return ResponseEntity.ok(service.obtenerReservaSinEstado(id));
	}
	
	@PutMapping("/check-in/{id}")
	public ResponseEntity<ReservaResponse> hacerCheckin(@PathVariable Long id){
		return ResponseEntity.ok(service.checkIn(id));
	}
	
	@PutMapping("/check-out/{id}")
	public ResponseEntity<ReservaResponse> hacerCheckOut(@PathVariable Long id){
		return ResponseEntity.ok(service.checkOut(id));
	}
	
	@PutMapping("/cancelar-reserva/{id}")
	public boolean cancelarReserva(@PathVariable Long id){
		return service.cancelar(id);
	}
	
	@GetMapping("/huesped/{idHuesped}/en-curso")
    boolean tieneReservasEnCurso(@PathVariable("idHuesped") Long idHuesped) {
		return service.tieneReservasEnCurso(idHuesped);
	}

}
