package fr.uga.l3miage.pc.Controllers;

import fr.uga.l3miage.pc.Requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.Responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.Services.ServeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/serveurs")
@RequiredArgsConstructor
public class ServeurController {

    private final ServeurService serveurService;

    @PostMapping("/creer")
    public ResponseEntity<ServeurResponseDTO> creerServeur(@RequestBody ServeurRequestDTO request) {
        ServeurResponseDTO serveur = serveurService.creerServeur(request);
        return ResponseEntity.ok(serveur);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServeurResponseDTO> obtenirServeur(@PathVariable Long id) {
        return ResponseEntity.ok(serveurService.getServeurById(id));
    }

    @GetMapping("/")
    public String afficherPage() {
        return "index.html"; // Si vous utilisez le répertoire "static"
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ServeurResponseDTO> mettreAJourStatus(@PathVariable Long id, @RequestParam String nouveauStatus) {
        return ResponseEntity.ok(serveurService.updateServeurStatus(id, nouveauStatus));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerServeur(@PathVariable Long id) {
        serveurService.supprimerServeur(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/action")
    public ResponseEntity<String> effectuerAction(@RequestParam String type) {
        String message;

        switch (type) {
            case "cooperer":
                // Logique pour l'action "coopérer"
                message = "Vous avez choisi de cooperer.";
                break;
            case "trahir":
                // Logique pour l'action "trahir"
                message = "Vous avez choisi de trahir.";
                break;
            case "abandonner":
                // Logique pour l'action "abandonner"
                message = "Vous avez abandonne la partie.";
                break;
            default:
                message = "Action inconnue.";
        }

        return ResponseEntity.ok(message);
    }

}