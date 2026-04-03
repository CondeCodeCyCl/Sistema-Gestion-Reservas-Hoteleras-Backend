package com.example.huespedes.services;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.commons.clients.ReservacionesClient;
import com.example.commons.dto.HuespedRequest;
import com.example.commons.dto.HuespedResponse;
import com.example.commons.enums.EstadoRegistro;
import com.example.commons.exceptions.DatosDuplicadosException;
import com.example.commons.exceptions.EntidadRelacionadaException;
import com.example.commons.exceptions.RecursoNoEncontradoException;
import com.example.huespedes.entities.Huesped;
import com.example.huespedes.mappers.HuespedMapper;
import com.example.huespedes.repositories.HuespedRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class HuespedServiceImpl implements HuespedService {

	private final HuespedRepository huespedRepository;
	private final HuespedMapper huespedMapper;
	private final ReservacionesClient reservacionesClient;

	@Override
	@Transactional(readOnly = true)
	public List<HuespedResponse> listar() {
		log.info("Listado de todos los pacientes activos solicitados");

		return huespedRepository.findByEstadoRegistro(EstadoRegistro.ACTIVO).stream()
				.map(huespedMapper::entityToResponse).toList();
	}

	@Override
	@Transactional(readOnly = true)
	public HuespedResponse obtenerPorId(Long id) {
		return huespedMapper.entityToResponse(obtenerHuespedException(id));
	}

	private Huesped obtenerHuespedException(Long id) {
		log.info("Buscando Huesped activo con id: {}", id);

		return huespedRepository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
				.orElseThrow(() -> new RecursoNoEncontradoException("Huesped activo no encontrado con id: " + id));
	}

	@Override
	public HuespedResponse registrar(HuespedRequest request) {
		log.info("Registrando nuevo Huesped: {}", request.nombre());

		validarEmailUnico(request.email());
		validarTelefonoUnico(request.telefono());
		validarDocumentoUnico(request.documento());
		
		Huesped huesped = huespedMapper.requestToEntity(request);
		
		huespedRepository.save(huesped);
		
		log.info("Nuevo huesped registrado con id: {}", huesped.getId());
		
		return huespedMapper.entityToResponse(huesped);
	}

	@Override
	public HuespedResponse actualizar(HuespedRequest request, Long id) {
		Huesped huesped = obtenerHuespedException(id);
		
		validarCambiosUnicos(request, id);
		
		log.info("Actualizando huesped con id: {}", id);
		
		huespedMapper.updateEntityFromRequest(request, huesped);
		
		log.info("Huesped con id {} actualizado", id);	
		return huespedMapper.entityToResponse(huesped);
	}

	@Override
	public void eliminar(Long id) {
		Huesped huesped = obtenerHuespedException(id);
		log.info("Eliminando huesped ... con id: {}", id);
		
		if(reservacionesClient.tieneReservasEnCurso(id)) {
			throw new EntidadRelacionadaException("No se puede eliminar el huésped porque tiene reservas EN_CURSO");
		}
		
		huesped.setEstadoRegistro(EstadoRegistro.ELIMINADO);
		
		log.info("Huesped con id {} ha sido marcado como eliminado", id);
	}

	private void validarEmailUnico(String email) {
		log.info("Buscando email unico ...");

		if (huespedRepository.existsByEmailAndEstadoRegistro(email.toLowerCase(), EstadoRegistro.ACTIVO)) {
			throw new RecursoNoEncontradoException("Ya existe un Email registrado con el email " + email);
		}
	}

	private void validarTelefonoUnico(String telefono) {
		log.info("Validando telefono unico");
		if (huespedRepository.existsByTelefonoAndEstadoRegistro(telefono, EstadoRegistro.ACTIVO)) {
			throw new RecursoNoEncontradoException("El teléfono ya está registrado en un huesped activo");
		}
	}
	
	private void validarDocumentoUnico(String documento) {
		log.info("Buscando documento unico ...");
		
		if(huespedRepository.existsByDocumentoAndEstadoRegistro(documento, EstadoRegistro.ACTIVO)) {
			throw new RecursoNoEncontradoException("El documento ya está registrado en un huesped activo");
		}
	}

	private void validarCambiosUnicos(HuespedRequest request, Long id) {
		if (huespedRepository.existsByEmailAndIdNotAndEstadoRegistro(request.email().toLowerCase(),id, EstadoRegistro.ACTIVO)) {
			throw new DatosDuplicadosException("El correo ya está registrado en un huesped activo con el email: " + request.email());
		}
		
		if (huespedRepository.existsByTelefonoAndIdNotAndEstadoRegistro(request.telefono().toLowerCase(), id, EstadoRegistro.ACTIVO)) {
			throw new DatosDuplicadosException("El teléfono ya está registrado en un huesped activo con el teléfono: " + request.telefono());
		}
		
		if(huespedRepository.existsByDocumentoAndIdNotAndEstadoRegistro(request.documento(), id, EstadoRegistro.ACTIVO)) {
			throw new DatosDuplicadosException("El documento ya está registrado en un huesped activo con el documento: " + request.documento());
		}
	}

}
