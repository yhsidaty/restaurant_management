package com.restaurant.repository;

import com.restaurant.model.Commande;
import com.restaurant.model.CommandeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
    List<Commande> findAllByOrderByDateDesc();
    List<Commande> findByStatus(CommandeStatus status);
    List<Commande> findByServeurIdOrderByDateDesc(Long serveurId);
}
