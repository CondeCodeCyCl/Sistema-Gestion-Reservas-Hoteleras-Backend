package com.example.habitaciones.services;

import com.example.commons.dto.HabitacionRequest;
import com.example.commons.dto.HabitacionResponse;
import com.example.commons.enums.EstadoHabitacion;
import com.example.commons.services.CrudService;

import java.util.List;

public interface HabitacionService extends CrudService<HabitacionRequest, HabitacionResponse>{
	
    
    HabitacionResponse cambiarEstado(Long id, EstadoHabitacion nuevoEstado);
}
