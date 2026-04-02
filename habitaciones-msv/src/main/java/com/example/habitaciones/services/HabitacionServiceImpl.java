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

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class HabitacionServiceImpl implements HabitacionService {

    private final HabitacionRepository repository;
    private final HabitacionMapper mapper; 

    @Override
    public HabitacionResponse registrar(HabitacionRequest request) {

        if (repository.existsByNumeroAndEstadoRegistro(request.numero(), EstadoRegistro.ACTIVO)) {
            throw new EntidadRelacionadaException("Ya existe una habitación activa con el número: " + request.numero());
        }


        Habitacion habitacion = mapper.requestToEntity(request);


        habitacion.setEstadoHabitacion(EstadoHabitacion.DISPONIBLE.getCodigo());
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
    @Transactional(readOnly = true)
    public List<HabitacionResponse> obtenerDisponiblesYActivas() {
        
        List<Habitacion> disponibles = repository.findByEstadoHabitacionAndEstadoRegistro(
                EstadoHabitacion.DISPONIBLE.getCodigo(), 
                EstadoRegistro.ACTIVO
        );
        return disponibles.stream()
                .map(habitacion -> mapper.entityToResponse(habitacion))
                .toList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public HabitacionResponse obtenerHabitacionPorIdSinEstado(Long id) {
        
        Habitacion habitacion = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró ninguna habitación con el ID: " + id));
                
        return mapper.entityToResponse(habitacion);
    }

    @Override
    public HabitacionResponse actualizar(HabitacionRequest request, Long id) {
        
        Habitacion habitacion = buscarHabitacionActiva(id);

        EstadoHabitacion estadoActual = EstadoHabitacion.fromCodigo(habitacion.getEstadoHabitacion());

        if (estadoActual == EstadoHabitacion.OCUPADA) {
            throw new IllegalArgumentException(
                "No se puede modificar la información de una habitación mientras se encuentra OCUPADA."
            );
        }

        habitacion.setNumero(request.numero());
        habitacion.setTipo(request.tipo());
        habitacion.setPrecio(request.precio());
        habitacion.setCapacidad(request.capacidad());
        return mapper.entityToResponse(repository.save(habitacion));
    }

    @Override
    public void eliminar(Long id) {
        Habitacion habitacion = buscarHabitacionActiva(id);

        if (habitacion.getEstadoHabitacion().equals(EstadoHabitacion.OCUPADA.getCodigo())) {
            throw new EntidadRelacionadaException("No se puede eliminar la habitación porque actualmente está OCUPADA.");
        }

        habitacion.setEstadoRegistro(EstadoRegistro.ELIMINADO);
        repository.save(habitacion);
    }

    @Override
    public HabitacionResponse cambiarEstado(Long id, Integer idEstado) {
        Habitacion habitacion = buscarHabitacionActiva(id);
        
        EstadoHabitacion estadoActual = EstadoHabitacion.fromCodigo(habitacion.getEstadoHabitacion());

        EstadoHabitacion nuevoEstado = EstadoHabitacion.fromCodigo(idEstado);

        if (!esTransicionValida(estadoActual, nuevoEstado)) {
            throw new IllegalArgumentException("Transición inválida de " + estadoActual + " a " + nuevoEstado);
        }

        habitacion.setEstadoHabitacion(nuevoEstado.getCodigo());
        
        return mapper.entityToResponse(repository.save(habitacion));
    }

    private Habitacion buscarHabitacionActiva(Long id) {
        return repository.findByIdAndEstadoRegistro(id, EstadoRegistro.ACTIVO)
                .orElseThrow(() -> new RecursoNoEncontradoException("No se encontró la habitación activa con ID: " + id));
    }

    private boolean esTransicionValida(EstadoHabitacion actual, EstadoHabitacion nuevo) {

        if (actual == nuevo) return false; 

        return switch (actual) {

            case DISPONIBLE -> nuevo == EstadoHabitacion.OCUPADA || nuevo == EstadoHabitacion.MANTENIMIENTO;
            case OCUPADA -> nuevo == EstadoHabitacion.LIMPIEZA;
            case LIMPIEZA -> nuevo == EstadoHabitacion.DISPONIBLE || nuevo == EstadoHabitacion.MANTENIMIENTO;
            case MANTENIMIENTO -> nuevo == EstadoHabitacion.LIMPIEZA || nuevo == EstadoHabitacion.DISPONIBLE;
        };
    }
}