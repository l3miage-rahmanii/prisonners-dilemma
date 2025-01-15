package fr.uga.l3miage.pc.controllers;



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

    @PostMapping("/parties/{partieId}/joueurs/{joueurId}/coup")
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
            }

        return ResponseEntity.ok(partieService.jouerCoup(joueurId,coupEnum));

    }

    @PostMapping("/parties/creer")
    public ResponseEntity<PartieResponseDTO> creerPartie(@RequestBody PartieRequestDTO partieRequestDTO) {
        return ResponseEntity.ok(partieService.creerPartie(partieRequestDTO));
    }

    /*
    @PostMapping("/parties/{partieId}/joueurs/{joueurId}/strategie")
    public ResponseEntity<PartieResponseDTO> changerStrategie(
            @PathVariable Long partieId,
            @PathVariable Long joueurId,
            @RequestParam StrategieEnum strategie) {
        return ResponseEntity.ok(partieService.changerStrategie(partieId, joueurId, strategie));
    }

     */

    @PostMapping("/parties/rejoindre")
    public ResponseEntity<PartieResponseDTO> rejoindrePartie() {
                    return ResponseEntity.ok(partieService.rejoindrePartie());
    }

    @GetMapping("/parties/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok(partieService.getStatus());
    }

    @GetMapping("/parties/joueurs/{joueurId}/score")
    public ResponseEntity<Integer> getScore(
            @PathVariable int joueurId) {
        return ResponseEntity.ok(partieService.calculerScores(joueurId));
    }
/*
    @GetMapping("/parties/{partieId}/messages")
    public ResponseEntity<String> getMessage(
            @PathVariable Long partieId) {
        return ResponseEntity.ok(partieService.getMessage(partieId));
    }


 */

}


