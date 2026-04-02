package com.example.habitaciones.services;

import com.example.commons.dto.HabitacionRequest;
import com.example.commons.dto.HabitacionResponse;
import com.example.commons.enums.EstadoHabitacion;
import com.example.commons.enums.EstadoRegistro;
import com.example.commons.exceptions.EntidadRelacionadaException;
import com.example.commons.exceptions.RecursoNoEncontradoException;
import com.example.habitaciones.entities.Habitacion;
import com.example.habitaciones.mappers.HabitacionMapper;
import com.example.habitaciones.repositories.HabitacionRepository;
import com.example.habitaciones.services.HabitacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository repository;
    private final HabitacionMapper mapper; 

    @Override
    @Transactional
    public HabitacionResponse registrar(HabitacionRequest request) {

        if (repository.existsByNumeroAndEstadoRegistro(request.numero(), EstadoRegistro.ACTIVO)) {
            throw new EntidadRelacionadaException("Ya existe una habitación activa con el número: " + request.numero());
        }


        Habitacion habitacion = mapper.requestToEntity(request);


        habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE);
        habitacion.setEstadoRegistro(EstadoRegistro.ACTIVO);


        return mapper.entityToResponse(repository.save(habitacion));
    }

    @Override
    @Transactional(readOnly = true)
    public List<HabitacionResponse> listar() {
        return repository.findAll().stream()
                .filter(h -> h.getEstadoRegistro() == EstadoRegistro.ACTIVO)
                .map(mapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerPorId(Long id) {
        Habitacion habitacion = buscarHabitacionActiva(id);
        return mapper.entityToResponse(habitacion);
    }

    @Override
    @Transactional
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        Habitacion habitacion = buscarHabitacionActiva(id);

        if (!habitacion.getNumero().equals(request.numero()) && 
            repository.existsByNumeroAndEstadoRegistro(request.numero(), EstadoRegistro.ACTIVO)) {
            throw new EntidadRelacionadaException("Ya existe otra habitación activa con el número: " + request.numero());
        }

        habitacion = mapper.updateEntityFromRequest(request, habitacion);

        return mapper.entityToResponse(repository.save(habitacion));
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Habitacion habitacion = buscarHabitacionActiva(id);

        if (habitacion.getEstadoHabitacion() == EstadoHabitacion.OCUPADA) {
            throw new EntidadRelacionadaException("No se puede eliminar la habitación porque actualmente está OCUPADA.");
        }

        habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        repository.save(habitacion);
    }

    @Override
    @Transactional
    public HabitacionResponse cambiarEstado(Long id, EstadoHabitacion nuevoEstado) {
        Habitacion habitacion = buscarHabitacionActiva(id);
        habitacion.setEstadoHabitacion(nuevoEstado);
        return mapper.entityToResponse(repository.save(habitacion));
    }

    private Habitacion buscarHabitacionActiva(Long id) {
        return repository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la habitación activa con ID: " + id));
    }
}