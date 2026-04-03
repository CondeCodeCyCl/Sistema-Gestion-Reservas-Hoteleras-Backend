package com.example.usuarios.controllers;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.commons.controllers.CommonController;
import com.example.usuarios.dto.UsuarioRequest;
import com.example.usuarios.dto.UsuarioResponse;
import com.example.usuarios.services.UsuarioService;


@RestController
@Validated
public class UsuarioController extends CommonController<UsuarioRequest, UsuarioResponse, UsuarioService>{
	
	public UsuarioController(UsuarioService service) {
		super(service);
	}
}
