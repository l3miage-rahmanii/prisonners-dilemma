package fr.uga.l3miage.pc.Endpoint;

import fr.uga.l3miage.pc.Requests.PartieRequestDTO;
import fr.uga.l3miage.pc.Responses.PartieResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Tag(name = "Gestion des parties", description = "Endpoints pour gérer les parties")
@RestController
@RequestMapping("/api/parties")
public interface PartieEndpoint {

    @Operation(description = "Récupérer une partie par son ID")
    @ApiResponse(
            responseCode = "200",
            description = "La partie a été récupérée avec succès"
    )
    @ApiResponse(
            responseCode = "404",
            description = "La partie demandée n'a pas été trouvée"
    )
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    PartieResponseDTO getPartieById(@PathVariable Long id);

    @Operation(description = "Mettre à jour le statut d'une partie")
    @ApiResponse(
            responseCode = "200",
            description = "Le statut de la partie a été mis à jour avec succès"
    )
    @ApiResponse(
            responseCode = "404",
            description = "La partie demandée n'a pas été trouvée"
    )
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}/status")
    PartieResponseDTO updatePartieStatus(@PathVariable Long id, @RequestParam String nouveauStatus);

    @Operation(description = "Démarrer une nouvelle partie")
    @ApiResponse(
            responseCode = "201",
            description = "La partie a été créée et démarrée avec succès"
    )
    @ApiResponse(
            responseCode = "400",
            description = "Il y a eu une erreur dans les données fournies"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/demarrer")
    PartieResponseDTO demarrerPartie(@RequestBody PartieRequestDTO request);
}
