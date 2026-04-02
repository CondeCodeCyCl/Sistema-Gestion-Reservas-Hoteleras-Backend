package com.example.huespedes.mappers;
import org.springframework.stereotype.Component;
import com.example.commons.dto.HuespedRequest;
import com.example.commons.dto.HuespedResponse;
import com.example.commons.enums.EstadoRegistro;
import com.example.commons.mappers.CommonMapper;
import com.example.huespedes.entities.Huesped;

@Component
public class HuespedMapper implements CommonMapper<HuespedRequest, HuespedResponse, Huesped> {

	@Override
	public Huesped requestToEntity(HuespedRequest request) {
		if(request == null) return null;
		
		return Huesped.builder()
				.nombre(request.nombre())
				.apellido(request.apellido())
				.email(request.email())
				.telefono(request.telefono())
				.documento(request.documento())
				.nacionalidad(request.nacionalidad())
				.estadoRegistro(EstadoRegistro.ACTIVO)
				.build();
	}

	@Override
	public HuespedResponse entityToResponse(Huesped entity) {
		if(entity == null) return null;
		return new HuespedResponse(
				entity.getId(),
				entity.getNombre(),
				entity.getApellido(),
				entity.getEmail(),
				entity.getTelefono(),
				entity.getDocumento(),
				entity.getNacionalidad()
				);
	}

	@Override
	public Huesped updateEntityFromRequest(HuespedRequest request, Huesped entity) {
		if(request == null || entity == null) return null;
		entity.setNombre(request.nombre());
		entity.setApellido(request.apellido());
		entity.setEmail(request.email());
		entity.setTelefono(request.telefono());
		entity.setDocumento(request.documento());
		entity.setNacionalidad(request.nacionalidad());
		return entity;
	}
}
