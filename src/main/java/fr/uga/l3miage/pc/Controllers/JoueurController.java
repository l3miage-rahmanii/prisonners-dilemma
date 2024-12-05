package fr.uga.l3miage.pc.Controllers;

import fr.uga.l3miage.pc.Services.JoueurService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/joueurs")
public class JoueurController {
    private final JoueurService joueurService;

    public JoueurController(JoueurService joueurService) {
        this.joueurService = joueurService;
    }

    @PostMapping("/{id}/nom")
    public String definirNomJoueur(@PathVariable Long id) {
        joueurService.enregistrerNomDuJoueur(id);
        return "Nom du joueur défini avec succès.";
    }

    @PostMapping("/{id}/tour")
    public String jouerTour(@PathVariable Long id) {
        joueurService.jouerTour(id);
        return "Tour joué avec succès.";
    }
}
