package com.nhst.medicoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class MedicoesApplication {

	public static void main(String[] args) {

		System.out.println("Senha: "+new BCryptPasswordEncoder().encode("admin"));
		SpringApplication.run(MedicoesApplication.class, args);
	}

}
