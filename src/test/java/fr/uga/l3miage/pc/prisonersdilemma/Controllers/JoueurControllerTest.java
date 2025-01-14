package fr.uga.l3miage.pc.prisonersdilemma.Controllers;


import fr.uga.l3miage.pc.controllers.JoueurController;
import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundJoueurEntityException;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import fr.uga.l3miage.pc.services.JoueurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class JoueurControllerTest {

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
        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(id);
        joueur.setNom("Test Joueur");
        joueur.setScore(nouveauScore);
        when(joueurService.updateScore(id, nouveauScore)).thenReturn(joueur);

        // When
        ResponseEntity<JoueurEntity> result = joueurController.mettreAJourScore(id, nouveauScore);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(id, result.getBody().getId());
        assertEquals("Test Joueur", result.getBody().getNom());
        assertEquals(nouveauScore, result.getBody().getScore());
    }

    @Test
    void testObtenirJoueurNotFound() {
        // Given
        Long id = 2L;
        when(joueurService.getJoueurById(id)).thenThrow(new NotFoundEntityRestException("Joueur introuvable"));

        // When
        ResponseEntity<JoueurResponseDTO> response = joueurController.obtenirJoueur(id);

        // Then
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testMettreAJourScoreNotFound() {
        // Given
        Long id = 2L;
        int nouveauScore = 200;
        when(joueurService.updateScore(id, nouveauScore)).thenThrow(new NotFoundEntityRestException("Joueur introuvable"));

        // When
        ResponseEntity<JoueurEntity> response = joueurController.mettreAJourScore(id, nouveauScore);

        // Then
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testMettreAJourScoreInvalidScore() {
        // Given
        Long id = 1L;
        int nouveauScore = -10; // Invalid score
        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(id);
        joueur.setNom("Test Joueur");
        joueur.setScore(nouveauScore);

        // When
        ResponseEntity<JoueurEntity> result = joueurController.mettreAJourScore(id, nouveauScore);

        // Then
        assertEquals(200, result.getStatusCodeValue());
    }

    @Test
    void testObtenirJoueurWithNullId() {
        // When
        ResponseEntity<JoueurResponseDTO> response = joueurController.obtenirJoueur(null);

        // Then
        assertEquals(200, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testMettreAJourScoreWithMaxScore() {
        // Given
        Long id = 1L;
        int nouveauScore = Integer.MAX_VALUE;
        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(id);
        joueur.setNom("Test Joueur");
        joueur.setScore(nouveauScore);
        when(joueurService.updateScore(id, nouveauScore)).thenReturn(joueur);

        // When
        ResponseEntity<JoueurEntity> result = joueurController.mettreAJourScore(id, nouveauScore);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(id, result.getBody().getId());
        assertEquals("Test Joueur", result.getBody().getNom());
        assertEquals(nouveauScore, result.getBody().getScore());
    }
}
