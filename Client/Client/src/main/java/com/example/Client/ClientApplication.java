package com.example.Client;

import com.example.Client.entities.Client;
import com.example.Client.repositories.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
public class ClientApplication {
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		SpringApplication.run(ClientApplication.class, args);
	}


	@Bean
	CommandLineRunner initialiserBaseH2(ClientRepository clientRepository) {
		return args -> {
			clientRepository.save(new Client(null, "Saad Chihab", 23m));
			clientRepository.save(new Client(null, "Saad Chihab2", 22m));
			clientRepository.save(new Client(null, "Saad Chihab3", 22m));
		};
	}
}
