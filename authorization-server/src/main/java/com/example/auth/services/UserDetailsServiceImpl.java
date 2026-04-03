package com.example.auth.services;

import com.example.auth.entities.Usuario;
import com.example.auth.repositories.UsuarioRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UserDetailsServiceImpl(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("=========================================================");
        System.out.println("1. SPRING INTENTA LOGUEAR A: '" + username + "'");

        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println("❌ ERROR: El usuario no existe en la base de datos a la que está conectada Spring.");
                    return new UsernameNotFoundException("Usuario no encontrado");
                });

        System.out.println("2. USUARIO ENCONTRADO EN ORACLE: '" + usuario.getUsername() + "'");
        System.out.println("3. HASH GUARDADO: '" + usuario.getPassword() + "'");
        System.out.println("4. ESTADO GUARDADO: '" + usuario.getEstado() + "'");
        
        // Hacemos el equalsIgnoreCase por si en Oracle se guardó como 'activo ' con espacios
        boolean estaEliminado = usuario.getEstado() != null && usuario.getEstado().trim().equalsIgnoreCase("ELIMINADO");
        System.out.println("5. ¿ESTÁ BLOQUEADO POR ESTADO?: " + estaEliminado);
        System.out.println("=========================================================");

        return User.builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword()) 
                .authorities(usuario.getRol().trim()) 
                .disabled(estaEliminado) 
                .build();
    }
}