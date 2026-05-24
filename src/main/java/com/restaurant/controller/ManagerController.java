package com.restaurant.controller;

import com.restaurant.model.*;
import com.restaurant.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final CommandeService commandeService;
    private final CategoryService categoryService;
    private final UserService userService;

    // ============ COMMANDES ============
    @GetMapping("/commandes")
    public String commandes(Model model) {

        List<Commande> commandes = commandeService.getAllCommandes();

        model.addAttribute("commandes", commandes);
        model.addAttribute("plats", categoryService.getAllPlats());
        model.addAttribute("statuses", CommandeStatus.values());
        model.addAttribute("activePage", "commandes");

        model.addAttribute("enAttenteCount",
                commandes.stream().filter(c -> c.getStatus() == CommandeStatus.EN_ATTENTE).count());

        model.addAttribute("enCoursCount",
                commandes.stream().filter(c -> c.getStatus() == CommandeStatus.EN_COURS).count());

        model.addAttribute("doneCount",
                commandes.stream().filter(c -> c.getStatus() == CommandeStatus.DONE).count());

        model.addAttribute("livreCount",
                commandes.stream().filter(c -> c.getStatus() == CommandeStatus.LIVRE).count());

        return "manager/commandes";
    }

    @PostMapping("/commandes/add")
    public String addCommande(@RequestParam Long platId,
                              @RequestParam Integer tableNumber,
                              Authentication auth,
                              RedirectAttributes ra) {
        try {
            commandeService.createCommande(platId, tableNumber, auth.getName());
            ra.addFlashAttribute("success", "Commande ajoutée avec succès");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/commandes";
    }

    @PostMapping("/commandes/delete/{id}")
    public String deleteCommande(@PathVariable Long id, RedirectAttributes ra) {
        try {
            commandeService.deleteCommande(id);
            ra.addFlashAttribute("success", "Commande supprimée");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/commandes";
    }

    // ============ MENU ============

    @GetMapping("/menu")
    public String menu(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("searchQuery", search);
        if (search != null && !search.isBlank()) {
            model.addAttribute("searchResults", categoryService.searchPlats(search));
        }
        model.addAttribute("activePage", "menu");
        return "manager/menu";
    }

    @PostMapping("/menu/categories/add")
    public String addCategory(@RequestParam String name, RedirectAttributes ra) {
        try {
            categoryService.createCategory(name);
            ra.addFlashAttribute("success", "Catégorie créée: " + name);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/menu";
    }

    @PostMapping("/menu/categories/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes ra) {
        try {
            categoryService.deleteCategory(id);
            ra.addFlashAttribute("success", "Catégorie supprimée");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/menu";
    }

    @PostMapping("/menu/plats/add")
    public String addPlat(@RequestParam Long categoryId,
                          @RequestParam String name,
                          @RequestParam String description,
                          @RequestParam Double price,
                          RedirectAttributes ra) {
        try {
            categoryService.createPlat(categoryId, name, description, price);
            ra.addFlashAttribute("success", "Plat ajouté: " + name);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/menu";
    }

    @PostMapping("/menu/plats/delete/{id}")
    public String deletePlat(@PathVariable Long id, RedirectAttributes ra) {
        try {
            categoryService.deletePlat(id);
            ra.addFlashAttribute("success", "Plat supprimé");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/menu";
    }

    // ============ PARAMETRES ============

    @GetMapping("/parametres")
    public String parametres(Model model, Authentication auth) {
        model.addAttribute("staff", userService.getAllStaff());
        model.addAttribute("roles", List.of(Role.SERVEUR, Role.CUISINER));
        model.addAttribute("activePage", "parametres");
        model.addAttribute("currentUser", userService.findByUsername(auth.getName()));
        return "manager/parametres";
    }

    @PostMapping("/parametres/password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Authentication auth,
                                 RedirectAttributes ra) {
        try {
            userService.changePassword(auth.getName(), oldPassword, newPassword);
            ra.addFlashAttribute("success", "Mot de passe modifié avec succès");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/parametres";
    }

    @PostMapping("/parametres/users/add")
    public String addUser(@RequestParam String username,
                          @RequestParam String password,
                          @RequestParam String fullName,
                          @RequestParam Role role,
                          RedirectAttributes ra) {
        try {
            userService.createUser(username, password, fullName, role);
            ra.addFlashAttribute("success", "Utilisateur créé: " + fullName);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/parametres";
    }

    @PostMapping("/parametres/users/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        try {
            userService.deleteUser(id);
            ra.addFlashAttribute("success", "Utilisateur supprimé");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/manager/parametres";
    }
}
