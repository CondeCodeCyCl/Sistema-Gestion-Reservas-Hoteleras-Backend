package com.example.usuarios.services;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.commons.enums.Roles;
import com.example.commons.exceptions.*;
import com.example.usuarios.dto.UsuarioRequest;
import com.example.usuarios.dto.UsuarioResponse;
import com.example.usuarios.entities.Usuario;
import com.example.usuarios.mappers.UsuarioMapper;
import com.example.usuarios.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImplement implements UsuarioService{
	
	private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder; 

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioResponse> listar() {
        log.info("Listado de todos los usuarios solicitado");
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::entityToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioResponse obtenerPorId(Long id) {
        return usuarioMapper.entityToResponse(obtenerUsuarioOException(id));
    }

    @Override
    public UsuarioResponse registrar(UsuarioRequest request) {
        log.info("Registrando nuevo usuario: {}", request.username());

        validarUsernameUnico(request.username());

        String passwordEncriptado = passwordEncoder.encode(request.password());
        
        Usuario usuario = usuarioMapper.requestToEntity(request, passwordEncriptado);
        
        usuarioRepository.save(usuario);
        
        log.info("Usuario registrado con éxito: {}", usuario.getUsername());
        return usuarioMapper.entityToResponse(usuario);
    }

    @Override
    public UsuarioResponse actualizar(UsuarioRequest request, Long id) {
        Usuario usuario = obtenerUsuarioOException(id);
        
        log.info("Actualizando usuario con id: {}", id);
        
        validarCambiosUnicos(request, id);
        
        usuario.setUsername(request.username());
        
        if (request.password() != null && !request.password().isBlank()) {
            usuario.setPassword(passwordEncoder.encode(request.password()));
        }
        
        try {
            usuario.setRol(Roles.valueOf(request.rol().toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("El rol proporcionado no es válido: " + request.rol());
        }

        log.info("Usuario actualizado con éxito: {}", id);
        return usuarioMapper.entityToResponse(usuarioRepository.save(usuario));
    }

    @Override
    public void eliminar(Long id) {
        Usuario usuario = obtenerUsuarioOException(id);
        
      
        usuarioRepository.delete(usuario);
        
        log.info("Usuario con id {} eliminado exitosamente", id);
    }
	

    
    private Usuario obtenerUsuarioOException(Long id) {
        log.info("Buscando usuario con id: {}", id);
        return usuarioRepository.findById(id).orElseThrow(() ->
                new RecursoNoEncontradoException("Usuario no encontrado con id: " + id));
    }

    private void validarUsernameUnico(String username) {
        log.info("Validando nombre de usuario único...");
        if (usuarioRepository.existsByUsername(username)) {
            throw new IllegalStateException("El nombre de usuario '" + username + "' ya se encuentra en uso");
        }
    }

    private void validarCambiosUnicos(UsuarioRequest request, Long id) {
        log.info("Validando unicidad para actualización de usuario...");
        if (usuarioRepository.existsByUsernameAndIdNot(request.username(), id)) {
            throw new IllegalArgumentException("El nombre de usuario '" + request.username() + "' ya está asignado a otro usuario");
        }
    }
    
}
