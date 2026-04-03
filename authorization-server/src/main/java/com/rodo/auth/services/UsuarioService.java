package com.rodo.auth.services;

import java.util.Set;

import com.rodo.auth.dto.UsuarioRequest;
import com.rodo.auth.dto.UsuarioResponse;

public interface UsuarioService {

    Set<UsuarioResponse> listar();

    UsuarioResponse registrar(UsuarioRequest request);

    UsuarioResponse eliminar(String username);
}

