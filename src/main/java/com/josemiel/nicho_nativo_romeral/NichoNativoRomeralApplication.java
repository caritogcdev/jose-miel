package com.josemiel.nicho_nativo_romeral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import com.josemiel.nicho_nativo_romeral.infrastructure.config.JwtProperties;

@EnableConfigurationProperties(JwtProperties.class)
@SpringBootApplication
public class NichoNativoRomeralApplication {

	public static void main(String[] args) {
		SpringApplication.run(NichoNativoRomeralApplication.class, args);
	}

}
