package com.example.huespedes.entities;
import com.example.commons.enums.EstadoRegistro;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "HUESPEDES")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class Huesped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID_HUESPED")
	private Long id;
	
	@Column(name = "NOMBRE", nullable = false, length = 50)
	private String nombre;
	
	@Column(name = "APELLIDO", nullable = false, length = 50)
	private String apellido;
	
	@Column(name = "EMAIL", nullable = false, length = 100)
	private String email;
	
	@Column(name = "TELEFONO", nullable = false, length = 10)
	private String telefono;
	
	@Column(name = "DOCUMENTO", nullable = false, length = 10)
	private String documento;
	
	@Column(name = "NACIONALIDAD", nullable = false, length = 50)
	private String nacionalidad;
	
	@Enumerated(EnumType.STRING)
	@Column(name= "ESTADO", nullable = false)
	private EstadoRegistro estadoRegistro;
}
