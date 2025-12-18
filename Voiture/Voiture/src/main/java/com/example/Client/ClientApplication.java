package com.example.Client;

import com.example.Client.entities.Client;
import com.example.Client.entities.Voiture;
import com.example.Client.repositories.VoitureRepository;
import com.example.Client.services.ClientService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableFeignClients
public class ClientApplication {

	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	CommandLineRunner initialiserBaseH2(VoitureRepository voitureRepository, ClientService clientService) {
		return args -> {
			// Retry simple pour attendre que le service CLIENT soit disponible via
			// Eureka/Feign
			int maxAttempts = 12; // ~1 minute
			int attempt = 0;
			Client c1 = null;
			Client c2 = null;
			while (attempt < maxAttempts) {
				try {
					c1 = clientService.clientById(2L);
					c2 = clientService.clientById(1L);
					break; // réussi
				} catch (Exception ex) {
					attempt++;
					System.out.println("Tentative " + attempt + " - service CLIENT indisponible encore, attente 5s...");
					Thread.sleep(5000);
				}
			}

			if (c1 == null || c2 == null) {
				System.err.println("Impossible de contacter le service CLIENT après " + maxAttempts
						+ " tentatives. Initialisation annulée.");
				return;
			}

			try {
				System.out.println("**************************");
				System.out.println("Id est :" + c2.getId());
				System.out.println("Nom est :" + c2.getNom());
				System.out.println("**************************");
				System.out.println("**************************");
				System.out.println("Id est :" + c1.getId());
				System.out.println("Nom est :" + c1.getNom());
				System.out.println("Age est :" + c1.getAge());
				System.out.println("**************************");
				voitureRepository.save(new Voiture(1L, "Toyota", "A 25 333", "Corolla", 1L, c2));
				voitureRepository.save(new Voiture(2L, "Renault", "B 6 3456", "Megane", 1L, c2));
				voitureRepository.save(new Voiture(3L, "Peugeot", "A 55 4444", "301", 2L, c1));
				System.out.println("Base de données Voiture initialisée avec succès !");
			} catch (Exception e) {
				System.err.println("Erreur lors de l'initialisation : " + e.getMessage());
				System.err.println("Assurez-vous que le service Client est démarré et enregistré dans Eureka.");
			}
		};
	}

}
