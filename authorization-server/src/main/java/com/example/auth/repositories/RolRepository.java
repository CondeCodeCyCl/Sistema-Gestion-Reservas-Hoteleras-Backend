package com.example.auth.repositories;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.auth.entities.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
	
	boolean existsByNombre(String nombre);
    
	Optional<Rol> findByNombre(String nombre);
}
