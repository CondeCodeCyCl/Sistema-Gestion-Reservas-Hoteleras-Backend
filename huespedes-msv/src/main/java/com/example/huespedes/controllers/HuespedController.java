package com.example.huespedes.controllers;
import org.springframework.web.bind.annotation.RestController;

import com.example.commons.controllers.CommonController;
import com.example.commons.dto.HuespedRequest;
import com.example.commons.dto.HuespedResponse;
import com.example.huespedes.services.HuespedService;

import jakarta.validation.Valid;

@RestController
@Valid
public class HuespedController extends CommonController<HuespedRequest, HuespedResponse, HuespedService>{

	public HuespedController(HuespedService service) {
		super(service);
	}

}
