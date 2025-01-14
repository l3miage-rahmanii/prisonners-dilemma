package fr.uga.l3miage.pc.controllers;


import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.services.ServeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/serveurs")
@RequiredArgsConstructor
public class ServeurController {
    private final ServeurService serveurService;

    @GetMapping
    public ResponseEntity<List<ServeurResponseDTO>> getAllServeurs() {
        return ResponseEntity.ok(serveurService.getAllServeurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServeurResponseDTO> getServeurById(@PathVariable Long id) {
        return ResponseEntity.ok(serveurService.getServeurById(id));
    }

    @PostMapping
    public ResponseEntity<ServeurResponseDTO> createServeur(@RequestBody ServeurRequestDTO request) {
        try {
            ServeurResponseDTO response = serveurService.createServeur(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (BadRequestRestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServeurResponseDTO> updateServeur(
            @PathVariable Long id,
            @RequestBody ServeurRequestDTO request) {
        return ResponseEntity.ok(serveurService.updateServeur(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServeur(@PathVariable Long id) {
        serveurService.deleteServeur(id);
        return ResponseEntity.noContent().build();
    }
}
