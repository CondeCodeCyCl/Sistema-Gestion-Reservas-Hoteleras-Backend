package com.example.reservas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.reservas.entities.Reservas;
import java.util.List;
import java.util.Optional;

import com.example.commons.enums.EstadoRegistro;


@Repository
public interface ReservaRepository extends JpaRepository<Reservas, Long>{
	
	List<Reservas> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	
	Optional<Reservas> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
	
	boolean existsByIdHuespedAndEstadoRegistro(Long idHuesped, EstadoRegistro estadoRegistro);

}
