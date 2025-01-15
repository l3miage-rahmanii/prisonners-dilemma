package fr.uga.l3miage.pc.controllers;



import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.server.UID;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class PartieController {
    private final PartieService partieService;

    @PostMapping("/parties/joueurs/{joueurId}/coup")
    public ResponseEntity<PartieResponseDTO> jouerCoup(
            @PathVariable int joueurId,
            @RequestParam String coup) {

        CoupEnum coupEnum = CoupEnum.COOPERER;
        // Normalize the move
        switch(coup.toLowerCase()) {
            case "cooperer":
                coupEnum = CoupEnum.COOPERER;
                break;
            case "trahir":
                coupEnum = CoupEnum.TRAHIR;
                break;
            case "abandonner":
                coupEnum = CoupEnum.ABANDONNER;
                break;
        }

        return ResponseEntity.ok(partieService.jouerCoup(joueurId, coupEnum));
    }

    @PostMapping("/parties/creer")
    public ResponseEntity<PartieEntity> creerPartie() {
        return ResponseEntity.ok(partieService.creerPartie());
    }

    @PostMapping("/parties/rejoindre")
    public ResponseEntity<PartieEntity> rejoindrePartie() {
        return ResponseEntity.ok(partieService.rejoindrePartie());
    }

    @GetMapping("/parties/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok(partieService.getStatus());
    }

    @GetMapping("/parties/joueurs/{joueurId}/score")
    public ResponseEntity<Integer> getScore(
            @PathVariable int joueurId) {
        return ResponseEntity.ok(partieService.getScore(joueurId));  // Changé de calculerScores à getScore
    }

    @GetMapping("/parties/joueurs/scores")
    public String getScores() {
        int score1 = partieService.getScore(1);  // Changé de calculerScores à getScore
        int score2 = partieService.getScore(2);  // Changé de calculerScores à getScore
        return String.format("""
        <p class="text-lg font-semibold text-gray-800">Joueur 1: <span id="score-player1">%d</span></p>
        <p class="text-lg font-semibold text-gray-800">Joueur 2: <span id="score-player2">%d</span></p>
        """, score1, score2);
    }
}