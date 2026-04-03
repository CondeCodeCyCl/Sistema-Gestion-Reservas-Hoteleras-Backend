package com.example.huespedes.services;
import org.springframework.stereotype.Repository;
import com.example.commons.dto.HuespedRequest;
import com.example.commons.dto.HuespedResponse;
import com.example.commons.services.CrudService;

@Repository
public interface HuespedService extends CrudService<HuespedRequest, HuespedResponse>{

	HuespedResponse obtenerHuespedSinEstado(Long id);
}
