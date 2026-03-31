package com.example.commons.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record HuespedRequest(
		
		@NotBlank(message = "El nombre es requerido")
		@Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
		String nombre,
		
		@NotBlank(message = "El email es requerido")
		@Email(message = "El email debe tener el formato correcto (correo@dominio)")
		String email,
		
		@NotBlank(message = "El teléfono es requerido")
        @Size(min = 10, max = 10, message = "El teléfono debe tener exactamente 10 dígitos")
        @Pattern(regexp = "^[0-9]{10}$", message = "El teléfono debe contener solo 10 dígitos numéricos")
        String telefono,
        
        @NotBlank(message = "El documento es requerido")
        String documento,
        
        @NotBlank(message = "La nacionalidad es requerida")
        String nacionalidad
        
) {}
