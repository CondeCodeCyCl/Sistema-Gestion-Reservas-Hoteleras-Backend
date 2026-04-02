package com.example.huespedes;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example.huespedes", "com.example.commons"})
public class HuespedesMsvApplication {

	public static void main(String[] args) {
		SpringApplication.run(HuespedesMsvApplication.class, args);
	}

}
