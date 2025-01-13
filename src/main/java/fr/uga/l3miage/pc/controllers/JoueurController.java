package fr.uga.l3miage.pc.controllers;


import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import fr.uga.l3miage.pc.services.JoueurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




@RestController
@RequestMapping("/api/joueurs")
@RequiredArgsConstructor
public class JoueurController {

    private final JoueurService joueurService;

    @GetMapping("/{id}")
    public ResponseEntity<JoueurResponseDTO> obtenirJoueur(@PathVariable Long id) {
        return ResponseEntity.ok(joueurService.getJoueurById(id));
    }

    @PutMapping("/{id}/score")
    public ResponseEntity<JoueurEntity> mettreAJourScore(@PathVariable Long id, @RequestParam int nouveauScore) {
        return ResponseEntity.ok(joueurService.updateScore(id, nouveauScore));
    }
}
