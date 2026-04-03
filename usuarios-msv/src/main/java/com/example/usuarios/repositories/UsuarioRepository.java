package com.example.usuarios.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.commons.enums.Roles;
import com.example.usuarios.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	
    Optional<Usuario> findByUsername(String username);
   
    List<Usuario> findByRol(Roles rol);


    boolean existsByUsername(String username);

    boolean existsByUsernameAndIdNot(String username, Long id);


}