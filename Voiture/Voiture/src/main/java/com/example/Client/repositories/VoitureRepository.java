package com.example.Client.repositories;

import com.example.Client.entities.Voiture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoitureRepository extends JpaRepository<Voiture, Long> {
    // Méthode pour retrouver les voitures par clientId (champ dans l'entité Voiture)
    List<Voiture> findByClientId(Long clientId);
}
