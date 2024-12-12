package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.JoueurController;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import fr.uga.l3miage.pc.Services.JoueurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class JoueurControllerTest {

    @Mock
    private JoueurService joueurService;

    @InjectMocks
    private JoueurController joueurController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenirJoueur() {
        // Given
        Long id = 1L;
        JoueurResponseDTO response = new JoueurResponseDTO();
        response.setId(id);
        response.setNom("Test Joueur");
        response.setScore(100);
        when(joueurService.getJoueurById(id)).thenReturn(response);

        // When
        ResponseEntity<JoueurResponseDTO> result = joueurController.obtenirJoueur(id);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(id, result.getBody().getId());
        assertEquals("Test Joueur", result.getBody().getNom());
        assertEquals(100, result.getBody().getScore());
    }

    @Test
    void testMettreAJourScore() {
        // Given
        Long id = 1L;
        int nouveauScore = 150;
        JoueurResponseDTO response = new JoueurResponseDTO();
        response.setId(id);
        response.setNom("Test Joueur");
        response.setScore(nouveauScore);

        when(joueurService.updateScore(eq(id), eq(nouveauScore))).thenReturn(response);

        // When
        ResponseEntity<JoueurResponseDTO> result = joueurController.mettreAJourScore(id, nouveauScore);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(id, result.getBody().getId());
        assertEquals("Test Joueur", result.getBody().getNom());
        assertEquals(nouveauScore, result.getBody().getScore());
    }
}
