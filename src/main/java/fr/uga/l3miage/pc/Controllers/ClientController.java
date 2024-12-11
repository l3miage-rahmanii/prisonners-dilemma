package fr.uga.l3miage.pc.Controllers;


import fr.uga.l3miage.pc.Requests.ClientRequestDTO;
import fr.uga.l3miage.pc.Responses.ClientResponseDTO;
import fr.uga.l3miage.pc.Services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping("/creer")
    public ResponseEntity<ClientResponseDTO> creerClient(@RequestBody ClientRequestDTO request) {
        return ResponseEntity.ok(clientService.creerClient(request));
    }

    @PostMapping("/{id}/demarrer-partie")
    public ResponseEntity<String> demarrerPartie(@PathVariable Long id) {
        clientService.demarrerPartie(id);
        return ResponseEntity.ok("Partie démarrée pour le client " + id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponseDTO> obtenirClient(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @PutMapping("/{id}/adresse")
    public ResponseEntity<ClientResponseDTO> mettreAJourAdresse(@PathVariable Long id, @RequestParam String nouvelleAdresse) {
        return ResponseEntity.ok(clientService.updateClientAdresse(id, nouvelleAdresse));
    }
}