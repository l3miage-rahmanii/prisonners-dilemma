package fr.uga.l3miage.pc.controllers;



import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartieController {
    private final PartieService partieService;

    @PostMapping("/{partieId}/joueurs/{joueurId}/coup")
    public ResponseEntity<PartieResponseDTO> jouerCoup(
            @PathVariable Long partieId,
            @PathVariable Long joueurId,
            @RequestParam String coup) {
        return ResponseEntity.ok(partieService.jouerCoup(partieId, joueurId, coup));
    }
}


