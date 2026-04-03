package com.example.commons.clients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.commons.configuration.FeignClientConfig;

@FeignClient(name = "reservas", configuration = FeignClientConfig.class)
public interface ReservacionesClient {

	@GetMapping("/huesped/{idHuesped}/en-curso")
    boolean tieneReservasEnCurso(@PathVariable("idHuesped") Long idHuesped);
}
