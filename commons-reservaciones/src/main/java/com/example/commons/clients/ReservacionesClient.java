package com.example.commons.clients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.commons.configuration.FeignClientConfig;

@FeignClient(name = "reservaciones-msv", configuration = FeignClientConfig.class)
public interface ReservacionesClient {

	@GetMapping("/api/reservaciones/huesped/{idHuesped}/en-curso")
    boolean tieneReservasEnCurso(@PathVariable("idHuesped") Long idHuesped);
}
