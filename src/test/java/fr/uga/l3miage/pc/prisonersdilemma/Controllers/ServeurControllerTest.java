package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.ServeurController;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.Services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ServeurControllerTest {

    @Mock
    private ServeurService serveurService;

    @InjectMocks
    private ServeurController serveurController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerServeur() {
        // Given
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("Actif");

        ServeurResponseDTO response = new ServeurResponseDTO();
        response.setId(1L);
        response.setStatus("Actif");

        when(serveurService.creerServeur(any(ServeurRequestDTO.class))).thenReturn(response);

        // When
        ResponseEntity<ServeurResponseDTO> result = serveurController.creerServeur(request);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Actif", result.getBody().getStatus());
        verify(serveurService, times(1)).creerServeur(any(ServeurRequestDTO.class));
    }

    @Test
    void testObtenirServeur() {
        // Given
        Long id = 1L;
        ServeurResponseDTO response = new ServeurResponseDTO();
        response.setId(id);
        response.setStatus("Actif");

        when(serveurService.getServeurById(id)).thenReturn(response);

        // When
        ResponseEntity<ServeurResponseDTO> result = serveurController.obtenirServeur(id);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(id, result.getBody().getId());
        assertEquals("Actif", result.getBody().getStatus());
        verify(serveurService, times(1)).getServeurById(id);
    }

    @Test
    void testMettreAJourStatus() {
        // Given
        Long id = 1L;
        String nouveauStatus = "En maintenance";
        ServeurResponseDTO response = new ServeurResponseDTO();
        response.setId(id);
        response.setStatus(nouveauStatus);

        when(serveurService.updateServeurStatus(eq(id), eq(nouveauStatus))).thenReturn(response);

        // When
        ResponseEntity<ServeurResponseDTO> result = serveurController.mettreAJourStatus(id, nouveauStatus);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(nouveauStatus, result.getBody().getStatus());
        verify(serveurService, times(1)).updateServeurStatus(eq(id), eq(nouveauStatus));
    }

    @Test
    void testSupprimerServeur() {
        // Given
        Long id = 1L;
        doNothing().when(serveurService).supprimerServeur(id);

        // When
        ResponseEntity<Void> result = serveurController.supprimerServeur(id);

        // Then
        assertEquals(204, result.getStatusCodeValue());
        verify(serveurService, times(1)).supprimerServeur(id);
    }

    @Test
    void testEffectuerActionCooperer() {
        // Given
        String type = "cooperer";

        // When
        ResponseEntity<String> result = serveurController.effectuerAction(type);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Vous avez choisi de cooperer.", result.getBody());
    }

    @Test
    void testEffectuerActionTrahir() {
        // Given
        String type = "trahir";

        // When
        ResponseEntity<String> result = serveurController.effectuerAction(type);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Vous avez choisi de trahir.", result.getBody());
    }

    @Test
    void testEffectuerActionAbandonner() {
        // Given
        String type = "abandonner";

        // When
        ResponseEntity<String> result = serveurController.effectuerAction(type);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Vous avez abandonne la partie.", result.getBody());
    }

    @Test
    void testEffectuerActionInconnue() {
        // Given
        String type = "inconnue";

        // When
        ResponseEntity<String> result = serveurController.effectuerAction(type);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Action inconnue.", result.getBody());
    }
}
