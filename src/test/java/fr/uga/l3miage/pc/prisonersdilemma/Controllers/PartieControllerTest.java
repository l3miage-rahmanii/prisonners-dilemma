package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.PartieController;
import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PartieControllerTest {

    @Mock
    private PartieService partieService;

    @InjectMocks
    private PartieController partieController;

    private PartieEntity partieEntity;
    private PartieResponseDTO partieResponseDTO;

    @BeforeEach
    void setUp() {
        partieEntity = PartieEntity.builder()
                .status("en_attente")
                .nbTours(10)
                .idJoueur1(1)
                .idJoueur2(2)
                .build();

        partieResponseDTO = new PartieResponseDTO();
    }

    @Test
    void jouerCoup_Cooperer() {
        // Given
        when(partieService.jouerCoup(1, CoupEnum.COOPERER)).thenReturn(partieResponseDTO);

        // When
        ResponseEntity<PartieResponseDTO> response = partieController.jouerCoup(1, "cooperer");

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(partieService).jouerCoup(1, CoupEnum.COOPERER);
    }

    @Test
    void jouerCoup_Trahir() {
        // Given
        when(partieService.jouerCoup(1, CoupEnum.TRAHIR)).thenReturn(partieResponseDTO);

        // When
        ResponseEntity<PartieResponseDTO> response = partieController.jouerCoup(1, "trahir");

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(partieService).jouerCoup(1, CoupEnum.TRAHIR);
    }

    @Test
    void jouerCoup_Abandonner() {
        // Given
        when(partieService.jouerCoup(1, CoupEnum.ABANDONNER)).thenReturn(partieResponseDTO);

        // When
        ResponseEntity<PartieResponseDTO> response = partieController.jouerCoup(1, "abandonner");

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        verify(partieService).jouerCoup(1, CoupEnum.ABANDONNER);
    }

    @Test
    void creerPartie_Success() {
        // Given
        when(partieService.creerPartie()).thenReturn(partieEntity);

        // When
        ResponseEntity<PartieEntity> response = partieController.creerPartie();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(partieEntity, response.getBody());
        verify(partieService).creerPartie();
    }

    @Test
    void rejoindrePartie_Success() {
        // Given
        when(partieService.rejoindrePartie()).thenReturn(partieEntity);

        // When
        ResponseEntity<PartieEntity> response = partieController.rejoindrePartie();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(partieEntity, response.getBody());
        verify(partieService).rejoindrePartie();
    }

    @Test
    void getStatus_Success() {
        // Given
        String expectedStatus = "en_cours";
        when(partieService.getStatus()).thenReturn(expectedStatus);

        // When
        ResponseEntity<String> response = partieController.getStatus();

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedStatus, response.getBody());
        verify(partieService).getStatus();
    }

    @Test
    void getScore_Success() {
        // Given
        int expectedScore = 10;
        when(partieService.getScore(1)).thenReturn(expectedScore);

        // When
        ResponseEntity<Integer> response = partieController.getScore(1);

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedScore, response.getBody());
        verify(partieService).getScore(1);
    }

    @Test
    void getScores_Success() {
        // Given
        when(partieService.getScore(1)).thenReturn(10);
        when(partieService.getScore(2)).thenReturn(15);

        // When
        String response = partieController.getScores();

        // Then
        assertNotNull(response);
        assertTrue(response.contains("10"));
        assertTrue(response.contains("15"));
        verify(partieService).getScore(1);
        verify(partieService).getScore(2);
    }

    @Test
    void jouerCoup_CoupInconnu() {
        // Given
        when(partieService.jouerCoup(1, CoupEnum.COOPERER)).thenReturn(partieResponseDTO);

        // When
        ResponseEntity<PartieResponseDTO> response = partieController.jouerCoup(1, "coup_inconnu");

        // Then
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        // Par défaut, un coup inconnu est traité comme COOPERER
        verify(partieService).jouerCoup(1, CoupEnum.COOPERER);
    }
}
