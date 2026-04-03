package com.example.auth.mappers;

import org.springframework.stereotype.Component;
import com.example.auth.dto.UsuarioRequest;
import com.example.auth.dto.UsuarioResponse;
import com.example.auth.entities.Usuario;

@Component
public class UsuarioMapper {

    public UsuarioResponse entityToResponse(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return new UsuarioResponse(usuario.getUsername(), usuario.getRol());
    }

    public Usuario requestToEntity(UsuarioRequest request, String passwordEncriptada) {
        if (request == null) {
            return null;
        }
        
        Usuario usuario = new Usuario();
        usuario.setUsername(request.username());
        usuario.setPassword(passwordEncriptada);
        usuario.setRol(request.rol());
        usuario.setEstado("ACTIVO"); 
        
        return usuario;
    }
}