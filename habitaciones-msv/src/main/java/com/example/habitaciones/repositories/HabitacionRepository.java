package com.example.habitaciones.repositories;

import com.example.habitaciones.entities.Habitacion;
import com.example.commons.enums.EstadoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HabitacionRepository extends JpaRepository<Habitacion, Long> {
    
    boolean existsByNumeroAndEstadoRegistro(Integer numero, EstadoRegistro estadoRegistro);

    Optional<Habitacion> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
    
    List<Habitacion> findByEstadoHabitacionAndEstadoRegistro(String estadoHabitacion, EstadoRegistro estadoRegistro);
}