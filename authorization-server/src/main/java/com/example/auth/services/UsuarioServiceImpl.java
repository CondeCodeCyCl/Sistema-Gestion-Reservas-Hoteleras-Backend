package com.example.auth.services;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.auth.dto.UsuarioRequest;
import com.example.auth.dto.UsuarioResponse;
import com.example.auth.entities.Usuario;
import com.example.auth.mappers.UsuarioMapper;
import com.example.auth.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Set<UsuarioResponse> listar() {
        log.info("Listado de todos los usuarios solicitado");
        return usuarioRepository.findAll().stream()
                .map(usuarioMapper::entityToResponse)
                .collect(Collectors.toSet());
    }

    @Override
    public UsuarioResponse registrar(UsuarioRequest request) {
        log.info("Validando usuario {}", request.username());
        if (usuarioRepository.findByUsername(request.username()).isPresent()) {
            throw new IllegalArgumentException("El usuario " + request.username() + " ya está registrado");
        }

        Usuario usuario = usuarioMapper.requestToEntity(request, passwordEncoder.encode(request.password()));

        usuario = usuarioRepository.save(usuario);
        return usuarioMapper.entityToResponse(usuario);
    }

    @Override
    public UsuarioResponse eliminar(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("No se encontró el usuario: " + username));
        
        usuario.setEstado("ELIMINADO");
        usuario = usuarioRepository.save(usuario);
        
        return usuarioMapper.entityToResponse(usuario);
    }
}