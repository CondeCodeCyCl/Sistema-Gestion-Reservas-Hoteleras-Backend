package com.example.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.usuarios", "com.example.commons"})
public class UsuariosMsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuariosMsvApplication.class, args);
	}

}
