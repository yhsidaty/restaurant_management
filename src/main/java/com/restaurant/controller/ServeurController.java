package com.restaurant.controller;

import com.restaurant.model.*;
import com.restaurant.service.CategoryService;
import com.restaurant.service.CommandeService;
import com.restaurant.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/serveur")
@RequiredArgsConstructor
public class ServeurController {

    private final CommandeService commandeService;
    private final CategoryService categoryService;
    private final UserService userService;

    @GetMapping("/commandes")
    public String commandes(Model model, Authentication auth) {
        User serveur = userService.findByUsername(auth.getName());
        model.addAttribute("commandes", commandeService.getCommandesByServeur(serveur.getId()));
        model.addAttribute("plats", categoryService.getAllPlats());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "serveur/commandes";
    }

    @PostMapping("/commandes/add")
    public String addCommande(@RequestParam Long platId,
                              @RequestParam Integer tableNumber,
                              Authentication auth,
                              RedirectAttributes ra) {
        try {
            commandeService.createCommande(platId, tableNumber, auth.getName());
            ra.addFlashAttribute("success", "Commande ajoutée — En attente");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/serveur/commandes";
    }

    @PostMapping("/commandes/delete/{id}")
    public String deleteCommande(@PathVariable Long id, RedirectAttributes ra) {
        try {
            Commande c = commandeService.findById(id);
            if (c.getStatus() != CommandeStatus.EN_ATTENTE) {
                ra.addFlashAttribute("error", "Seules les commandes 'En Attente' peuvent être supprimées");
            } else {
                commandeService.deleteCommande(id);
                ra.addFlashAttribute("success", "Commande supprimée");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/serveur/commandes";
    }

    @PostMapping("/commandes/livrer/{id}")
    public String livrerCommande(@PathVariable Long id, RedirectAttributes ra) {
        try {
            commandeService.updateStatus(id, CommandeStatus.LIVRE);
            ra.addFlashAttribute("success", "Commande marquée comme Livrée");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/serveur/commandes";
    }
}
