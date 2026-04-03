package com.example.usuarios.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(
		
		@NotBlank(message = "El username es requerido")
	    @Size(min = 5, max = 20, message = "El username debe tener entre 5 y 20 caracteres")
	    String username,
	    
	    @NotBlank(message = "La contraseña es requerida")
	    @Size(min = 8, message = "La contraseña debe tener entre 8 y 20 caracteres")
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z0-9]{8,}$", message = "La contraseña debe contener letras y números")
	    String password,
	    
	    @NotBlank(message = "El rol es requerido")
	    String rol

) {}
