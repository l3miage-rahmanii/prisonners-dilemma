package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.ServeurController;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
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

        when(serveurService.createServeur(any(ServeurRequestDTO.class))).thenReturn(response);

        // When
        ResponseEntity<ServeurResponseDTO> result = serveurController.createServeur(request);

        // Then
        assertEquals(HttpStatus.CREATED, result.getStatusCode()); // Vérifie le code de statut HTTP
        assertEquals("Actif", result.getBody().getStatus()); // Vérifie la valeur dans le corps de la réponse
        verify(serveurService, times(1)).createServeur(any(ServeurRequestDTO.class));
    }


    @Test
    void testCreerServeurAvecPartieActive() {
        // Given
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("Actif");

        when(serveurService.createServeur(any(ServeurRequestDTO.class)))
                .thenThrow(new BadRequestRestException("Un serveur avec une partie active existe déjà"));

        // When
        ResponseEntity<ServeurResponseDTO> responseEntity = serveurController.createServeur(request);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());  // Check for 400 Bad Request
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
        ResponseEntity<ServeurResponseDTO> result = serveurController.getServeurById(id);

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
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

        // Use matchers for all arguments
        when(serveurService.updateServeur(eq(id), any(ServeurRequestDTO.class))).thenReturn(response);

        // When
        ResponseEntity<ServeurResponseDTO> result = serveurController.updateServeur(id, new ServeurRequestDTO());

        // Then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(nouveauStatus, result.getBody().getStatus());
        verify(serveurService, times(1)).updateServeur(eq(id), any(ServeurRequestDTO.class));
    }


    @Test
    void testSupprimerServeur() {
        // Given
        Long id = 1L;
        doNothing().when(serveurService).deleteServeur(id);

        // When
        ResponseEntity<Void> result = serveurController.deleteServeur(id);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(serveurService, times(1)).deleteServeur(id);
    }

    @Test
    void testSupprimerServeurAvecPartieEnCours() {
        // Given
        Long id = 1L;
        doThrow(new BadRequestRestException("Impossible de supprimer un serveur avec une partie en cours"))
                .when(serveurService).deleteServeur(id);

        // When
        BadRequestRestException exception = assertThrows(BadRequestRestException.class,
                () -> serveurController.deleteServeur(id));

        // Then
        assertEquals("Impossible de supprimer un serveur avec une partie en cours", exception.getMessage());
    }
}
