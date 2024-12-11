package fr.uga.l3miage.pc.Controllers;


import fr.uga.l3miage.pc.Endpoint.PartieEndpoint;
import fr.uga.l3miage.pc.Requests.PartieRequestDTO;
import fr.uga.l3miage.pc.Responses.PartieResponseDTO;
import fr.uga.l3miage.pc.Services.PartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/parties")
@RequiredArgsConstructor
public class PartieController implements PartieEndpoint {
    private final PartieService partieService;

    @Override
    public PartieResponseDTO getPartieById(Long id) {
        return partieService.getPartieById(id);
    }

    @Override
    public PartieResponseDTO updatePartieStatus(Long id, String nouveauStatus) {
        return partieService.updatePartieStatus(id, nouveauStatus);
    }

    @Override
    public PartieResponseDTO demarrerPartie(PartieRequestDTO request) {
        return partieService.demarrerPartie(request);
    }
}
