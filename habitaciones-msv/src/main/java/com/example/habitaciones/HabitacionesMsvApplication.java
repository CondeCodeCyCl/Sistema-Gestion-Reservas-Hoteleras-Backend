package com.example.habitaciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.habitaciones", "com.example.commons"})
public class HabitacionesMsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitacionesMsvApplication.class, args);
	}

}
