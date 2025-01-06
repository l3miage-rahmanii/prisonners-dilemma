package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.ServeurController;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ServeurControllerTest {

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


        when(serveurService.updateServeurStatus(id, nouveauStatus)).thenReturn(response);

        // When
        ResponseEntity<ServeurResponseDTO> result = serveurController.mettreAJourStatus(id, nouveauStatus);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(nouveauStatus, result.getBody().getStatus());
        verify(serveurService, times(1)).updateServeurStatus(id, nouveauStatus);
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

    /**
     * Consolidated test for effectuerAction, using multiple scenarios (cooperer, trahir, abandonner, inconnue).
     */
    @ParameterizedTest(name = "When action = {0}, response should be: {1}")
    @MethodSource("provideEffectuerActionScenarios")
    void testEffectuerActionParameterized(String actionType, String expectedResponse) {
        // When
        ResponseEntity<String> result = serveurController.effectuerAction(actionType);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(expectedResponse, result.getBody());
    }

    /**
     * Provides the different action + expected response pairs
     * that used to be in separate test methods.
     */
    private static Stream<Arguments> provideEffectuerActionScenarios() {
        return Stream.of(
                Arguments.of("cooperer", "Vous avez choisi de cooperer."),
                Arguments.of("trahir", "Vous avez choisi de trahir."),
                Arguments.of("abandonner", "Vous avez abandonne la partie."),
                Arguments.of("inconnue", "Action inconnue.")
        );
    }
}
