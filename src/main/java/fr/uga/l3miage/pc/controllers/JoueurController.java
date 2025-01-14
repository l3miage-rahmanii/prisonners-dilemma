package fr.uga.l3miage.pc.controllers;


import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundJoueurEntityException;
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
        try {
            JoueurResponseDTO joueur = joueurService.getJoueurById(id);
            return ResponseEntity.ok(joueur);
        } catch (NotFoundEntityRestException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PutMapping("/{id}/score")
    public ResponseEntity<JoueurEntity> mettreAJourScore(@PathVariable Long id, @RequestParam int nouveauScore) {
        try {
            JoueurEntity joueur = joueurService.updateScore(id, nouveauScore);
            return ResponseEntity.ok(joueur);
        } catch (NotFoundEntityRestException e) {
            return ResponseEntity.status(404).build();  // Return 404 if player not found
        }
    }
}
