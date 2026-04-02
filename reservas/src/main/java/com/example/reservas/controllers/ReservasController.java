package com.example.reservas.controllers;

import org.springframework.validation.annotation.Validated;
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

}
