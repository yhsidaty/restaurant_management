package com.restaurant.service;

import com.restaurant.model.*;
import com.restaurant.repository.CommandeRepository;
import com.restaurant.repository.PlatRepository;
import com.restaurant.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final PlatRepository platRepository;
    private final UserRepository userRepository;

    public List<Commande> getAllCommandes() {
        return commandeRepository.findAllByOrderByDateDesc();
    }

    public List<Commande> getCommandesByServeur(Long serveurId) {
        return commandeRepository.findByServeurIdOrderByDateDesc(serveurId);
    }

    public Commande findById(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
    }

    @Transactional
    public Commande createCommande(Long platId, Integer tableNumber, String serveurUsername) {
        Plat plat = platRepository.findById(platId)
                .orElseThrow(() -> new RuntimeException("Plat non trouvé"));
        User serveur = userRepository.findByUsername(serveurUsername)
                .orElse(null);

        Commande commande = Commande.builder()
                .plat(plat)
                .tableNumber(tableNumber)
                .status(CommandeStatus.EN_ATTENTE)
                .date(LocalDateTime.now())
                .serveur(serveur)
                .build();
        return commandeRepository.save(commande);
    }

    @Transactional
    public void deleteCommande(Long id) {
        commandeRepository.deleteById(id);
    }

    @Transactional
    public Commande updateStatus(Long id, CommandeStatus newStatus) {
        Commande commande = findById(id);
        commande.setStatus(newStatus);
        return commandeRepository.save(commande);
    }
}
