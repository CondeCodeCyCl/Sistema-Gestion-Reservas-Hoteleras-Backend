package com.example.huespedes.repositories;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.commons.enums.EstadoRegistro;
import com.example.huespedes.entities.Huesped;

@Repository
public interface HuespedRepository extends JpaRepository<Huesped, Long>{
	
	List<Huesped> findByEstadoRegistro(EstadoRegistro estadoRegistro);
	Optional<Huesped> findByIdAndEstadoRegistro(Long id, EstadoRegistro estadoRegistro);
	Optional<Huesped> findById(Long id);

	
	boolean existsByEmailAndEstadoRegistro(String email, EstadoRegistro estadoRegistro);
	boolean existsByEmailAndIdNotAndEstadoRegistro(String email, Long id, EstadoRegistro estadoRegistro);
	
	boolean existsByTelefonoAndEstadoRegistro(String telefono, EstadoRegistro estadoRegistro);
	boolean existsByTelefonoAndIdNotAndEstadoRegistro(String telefono, Long id, EstadoRegistro estadoRegistro);
	
	boolean existsByDocumentoAndEstadoRegistro(String documento, EstadoRegistro estadoRegistro);
	boolean existsByDocumentoAndIdNotAndEstadoRegistro(String documento, Long id, EstadoRegistro estadoRegistro);


}
