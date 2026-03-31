package com.example.commons.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		
		@NotBlank(message = "El nombre de usuario es requerido")
		@Size(min = 5, max = 20, message = "El nombre de usuario debe tener entre 2 y 50 caracteres")
		String username,
		
		@NotBlank(message = "La contraseña es obligatoria")
		@Size(min = 8, message = "La contraeña debe tener minimo 8 caracteres")
		String password,
		
		@NotBlank(message = "El rol es requerido")
		String rol
		
) {
}
