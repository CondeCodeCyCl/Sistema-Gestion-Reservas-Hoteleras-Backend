package com.example.auth.services;

import java.util.Set;

import com.example.auth.dto.UsuarioRequest;
import com.example.auth.dto.UsuarioResponse;

public interface UsuarioService {
	
	Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);

}
