package com.example.commons.clients;

import org.springframework.cloud.openfeign.FeignClient;

import com.example.commons.configuration.FeignClientConfig;

@FeignClient(name = "reservaciones-msv", configuration = FeignClientConfig.class)
public interface ReservacionesClient {

}
