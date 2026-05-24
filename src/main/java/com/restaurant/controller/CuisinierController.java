package com.restaurant.controller;

import com.restaurant.model.CommandeStatus;
import com.restaurant.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cuisiner")
@RequiredArgsConstructor
public class CuisinierController {

    private final CommandeService commandeService;

    @GetMapping("/commandes")
    public String commandes(Model model) {
        model.addAttribute("commandes", commandeService.getAllCommandes());
        model.addAttribute("statuses", CommandeStatus.values());
        return "cuisiner/commandes";
    }

    @PostMapping("/commandes/status/{id}")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam CommandeStatus status,
                               RedirectAttributes ra) {
        try {
            commandeService.updateStatus(id, status);
            ra.addFlashAttribute("success", "Statut mis à jour: " + status.getLabel());
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/cuisiner/commandes";
    }
}
