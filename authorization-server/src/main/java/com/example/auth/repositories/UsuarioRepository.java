package com.example.auth.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.auth.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

}
