package com.example.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.commons.configuration.FeignClientConfig;
import com.example.commons.dto.HuespedResponse;

@FeignClient(name = "huespedes-msv", configuration = FeignClientConfig.class)
public interface HuespedesClient {
	
	@GetMapping("/{id}")
	HuespedResponse obtenerHuespedPorId(@PathVariable Long id);

}
