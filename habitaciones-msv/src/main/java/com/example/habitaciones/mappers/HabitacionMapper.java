package com.example.habitaciones.mappers;

import com.example.commons.dto.HabitacionRequest;
import com.example.commons.dto.HabitacionResponse;
import com.example.commons.mappers.CommonMapper;
import com.example.habitaciones.entities.Habitacion;
import org.springframework.stereotype.Component;

@Component
public class HabitacionMapper implements CommonMapper<HabitacionRequest, HabitacionResponse, Habitacion> {

    @Override
    public Habitacion requestToEntity(HabitacionRequest request) {
        return Habitacion.builder()
                .numero(request.numero())
                .tipo(request.tipo())
                .precio(request.precio())
                .capacidad(request.capacidad()) 
                .build();
    }

    @Override
    public HabitacionResponse entityToResponse(Habitacion entity) {
        return new HabitacionResponse(
                entity.getId(),
                entity.getNumero(),
                entity.getTipo(),
                entity.getPrecio(),
                entity.getCapacidad().shortValue()
        );
    }

    @Override
    public Habitacion updateEntityFromRequest(HabitacionRequest request, Habitacion entity) {
        entity.setNumero(request.numero());
        entity.setTipo(request.tipo());
        entity.setPrecio(request.precio());
        entity.setCapacidad(request.capacidad());
        return entity;
    }
}