package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.PartieController;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PartieControllerTest {

    @Mock
    private PartieService partieService;

    @InjectMocks
    private PartieController partieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPartieById() {
        // Given
        Long id = 1L;
        PartieResponseDTO response = new PartieResponseDTO();
        response.setId(id);
        response.setStatus("En cours");

        when(partieService.getPartieById(id)).thenReturn(response);

        // When
        PartieResponseDTO result = partieController.getPartieById(id);

        // Then
        assertEquals(id, result.getId());
        assertEquals("En cours", result.getStatus());
    }

    @Test
    void testUpdatePartieStatus() {
        // Given
        Long id = 1L;
        String nouveauStatus = "Terminé";
        PartieResponseDTO response = new PartieResponseDTO();
        response.setId(id);
        response.setStatus(nouveauStatus);

        // Remove eq(...) usage and pass values directly
        when(partieService.updatePartieStatus(id, nouveauStatus)).thenReturn(response);

        // When
        PartieResponseDTO result = partieController.updatePartieStatus(id, nouveauStatus);

        // Then
        assertEquals(id, result.getId());
        assertEquals(nouveauStatus, result.getStatus());
    }

    @Test
    void testDemarrerPartie() {
        // Given
        PartieRequestDTO request = new PartieRequestDTO();
        request.setNom("Nouvelle Partie");

        PartieResponseDTO response = new PartieResponseDTO();
        response.setId(1L);
        response.setNom("Nouvelle Partie");

        when(partieService.demarrerPartie(any(PartieRequestDTO.class))).thenReturn(response);

        // When
        PartieResponseDTO result = partieController.demarrerPartie(request);

        // Then
        assertEquals(1L, result.getId());
        assertEquals("Nouvelle Partie", result.getNom());
    }
}
