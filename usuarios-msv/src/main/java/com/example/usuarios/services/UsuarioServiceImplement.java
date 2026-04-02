package com.example.usuarios.services;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.usuarios.dto.UsuarioRequest;
import com.example.usuarios.dto.UsuarioResponse;
import com.example.usuarios.entities.Rol;
import com.example.usuarios.entities.Usuario;
import com.example.usuarios.mappers.UsuarioMapper;
import com.example.usuarios.repositories.RolRepository;
import com.example.usuarios.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImplement implements UsuarioService{
	
	private final UsuarioRepository usuarioRepository;

	private final PasswordEncoder passwordEncoder;

	@Override
	public Set<UsuarioResponse> listar() {
		log.info("Listado de todos los usuarios solicitado");
		return usuarioRepository.findAll().stream()
				.map(usuarioMapper::entityToResponse).collect(Collectors.toSet());
	}

	@Override
	public UsuarioResponse registrar(UsuarioRequest request) {
		log.info("Buscando usuario {}", request.username());
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("El usuario " + request.username() + " ya está registrado");
        }

        Set<Rol> roles = request.roles().stream().map(rol ->
                rolRepository.findByNombre(rol).orElseThrow(() ->
                        new NoSuchElementException("Rol " + rol + " no encontrado"))
        ).collect(Collectors.toSet());

        Usuario usuario = usuarioMapper.requestToEntity(request,
                passwordEncoder.encode(request.password()), roles);

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.entityToResponse(usuario);
	}

	@Override
	public UsuarioResponse eliminar(String username) {
		Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario: " + username));
        usuarioRepository.delete(usuario);
        return usuarioMapper.entityToResponse(usuario);
	}

}
