package fr.uga.l3miage.pc.controllers;



import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class PartieController {
    private final PartieService partieService;

    @PostMapping("/parties/{partieId}/joueurs/{joueurId}/coup")
    public ResponseEntity<String> jouerCoup(
            @PathVariable Long partieId,
            @PathVariable Long joueurId,
            @RequestParam String coup) {
        try {
            String coupNormalise;
            // Normalize the move
            switch(coup.toLowerCase()) {
                case "cooperer":
                    coupNormalise = "c";
                    break;
                case "trahir":
                    coupNormalise = "t";
                    break;
                case "abandonner":
                    partieService.changerStrategie(partieId, joueurId, StrategieEnum.TOUJOURS_TRAHIR);
                    return ResponseEntity.ok("Partie abandonnee");
                case "reinitialiser":
                    // Create new game
                    PartieRequestDTO newGame = PartieRequestDTO.builder()
                            .nom("Nouvelle Partie")
                            .nbTours(10)
                            .build();
                    PartieResponseDTO newPartie = partieService.creerPartie(newGame);
                    return ResponseEntity.ok("Nouvelle partie creee: " + newPartie.getId());
                default:
                    return ResponseEntity.badRequest().body("Coup invalide");
            }

            PartieResponseDTO response = partieService.jouerCoup(partieId, joueurId, coupNormalise);

            // Build a detailed response message
            StringBuilder message = new StringBuilder();
            message.append("Coup joue. ");

            // Check if it's player 1 or 2 based on their last moves
            boolean isJoueur1 = response.getCoupsJoueur1() != null &&
                    !response.getCoupsJoueur1().isEmpty() &&
                    response.getCoupsJoueur1().get(response.getCoupsJoueur1().size() - 1).equals(coupNormalise);

            int score = isJoueur1 ? response.getScoreJoueur1() : response.getScoreJoueur2();
            message.append("Score: ").append(score);

            // Add game status information
            if (response.isPartieTerminee()) {
                message.append(". ").append(response.getMessageResultat());
            } else {
                message.append(". Tour ").append(response.getTourActuel());
            }

            return ResponseEntity.ok(message.toString());

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/parties/creer")
    public ResponseEntity<PartieResponseDTO> creerPartie(@RequestBody PartieRequestDTO partieRequestDTO) {
        return ResponseEntity.ok(partieService.creerPartie(partieRequestDTO));
    }

    @PostMapping("/parties/{partieId}/joueurs/{joueurId}/strategie")
    public ResponseEntity<PartieResponseDTO> changerStrategie(
            @PathVariable Long partieId,
            @PathVariable Long joueurId,
            @RequestParam StrategieEnum strategie) {
        return ResponseEntity.ok(partieService.changerStrategie(partieId, joueurId, strategie));
    }

    @PostMapping("/parties/{partieId}/rejoindre")
    public ResponseEntity<PartieResponseDTO> rejoindrePartie(
            @PathVariable Long partieId,
            @PathVariable Long joueurId) {
        return ResponseEntity.ok(partieService.rejoindrePartie(partieId, joueurId));
    }


}


