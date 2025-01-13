package fr.uga.l3miage.pc.controllers;



import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
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
    @PostMapping("/creer")
    public PartieResponseDTO creerPartie(@RequestBody PartieRequestDTO partieRequestDTO) {
        return partieService.creerPartie(partieRequestDTO);
    }

    @PostMapping("/changerStrategie/{partieId}/{joueurId}")
    public PartieResponseDTO changerStrategie(@PathVariable Long partieId, @PathVariable Long joueurId, @RequestParam StrategieEnum strategie) {
        return partieService.changerStrategie(partieId, joueurId, strategie);
    }

    @PostMapping("/rejoindre/{partieId}/{joueurId}")
    public PartieResponseDTO rejoindrePartie(@PathVariable Long partieId, @PathVariable Long joueurId) {
        return partieService.rejoindrePartie(partieId, joueurId);
    }
}


