package br.com.somar.project.somar;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class SomarApplication {

	public static void main(String[] args) {
		SpringApplication.run(SomarApplication.class, args);
	}

	@PostConstruct
	public void init() {
		try {
			// Aguarda 30 segundos antes de inicializar conexões
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
