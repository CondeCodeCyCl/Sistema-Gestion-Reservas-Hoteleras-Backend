package com.example.usuarios.mappers;


import org.springframework.stereotype.Component;

import com.example.commons.enums.Roles;
import com.example.usuarios.dto.UsuarioRequest;
import com.example.usuarios.dto.UsuarioResponse;
import com.example.usuarios.entities.Usuario;

@Component
public class UsuarioMapper {
	

	public UsuarioResponse entityToResponse(Usuario usuario) {
		if (usuario == null) return null;
		

		return new UsuarioResponse(
				usuario.getId(),
				usuario.getUsername(),
				usuario.getRol().name()
		);
	}

	
	public Usuario requestToEntity(UsuarioRequest request, String passwordEncriptado) {
		if (request == null) return null;
		
		Usuario usuario = new Usuario();
		usuario.setUsername(request.username());
		usuario.setPassword(passwordEncriptado);
		
	
		usuario.setRol(Roles.valueOf(request.rol().toUpperCase()));
		
		return usuario;
	}
}
