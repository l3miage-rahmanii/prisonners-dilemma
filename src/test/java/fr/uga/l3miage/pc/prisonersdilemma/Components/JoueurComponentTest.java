package fr.uga.l3miage.pc.prisonersdilemma.Components;

import fr.uga.l3miage.pc.Components.JoueurComponent;
import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundJoueurEntityException;
import fr.uga.l3miage.pc.Repositories.JoueurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class JoueurComponentTest {

    @Mock
    private JoueurRepository joueurRepository;

    @InjectMocks
    private JoueurComponent joueurComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetJoueurByIdSuccess() throws NotFoundJoueurEntityException {
        // Given
        Long id = 1L;
        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(id);
        joueur.setNom("Test Joueur");
        when(joueurRepository.findById(id)).thenReturn(Optional.of(joueur));

        // When
        JoueurEntity result = joueurComponent.getJoueurById(id);

        // Then
        assertEquals(joueur, result);
        assertEquals("Test Joueur", result.getNom());
        verify(joueurRepository, times(1)).findById(id);
    }

    @Test
    void testGetJoueurByIdNotFound() {
        // Given
        Long id = 1L;
        when(joueurRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundJoueurEntityException.class, () -> joueurComponent.getJoueurById(id));
        verify(joueurRepository, times(1)).findById(id);
    }

    @Test
    void testUpdateScoreSuccess() throws NotFoundJoueurEntityException, BadRequestException {
        // Given
        Long id = 1L;
        int nouveauScore = 100;
        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(id);
        joueur.setScore(50);
        when(joueurRepository.findById(id)).thenReturn(Optional.of(joueur));
        when(joueurRepository.save(joueur)).thenReturn(joueur);

        // When
        JoueurEntity result = joueurComponent.updateScore(id, nouveauScore);

        // Then
        assertNotNull(result);
        assertEquals(nouveauScore, result.getScore());
        verify(joueurRepository, times(1)).findById(id);
        verify(joueurRepository, times(1)).save(joueur);
    }

    @Test
    void testUpdateScoreInvalid() {
        // Given
        Long id = 1L;
        int nouveauScore = -10;
        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(id);
        when(joueurRepository.findById(id)).thenReturn(Optional.of(joueur));

        // When/Then
        assertThrows(BadRequestException.class, () -> joueurComponent.updateScore(id, nouveauScore));
        verify(joueurRepository, times(1)).findById(id);
        verify(joueurRepository, times(0)).save(joueur);
    }

    @Test
    void testDeleteJoueurSuccess() throws NotFoundJoueurEntityException {
        // Given
        Long id = 1L;
        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(id);
        when(joueurRepository.findById(id)).thenReturn(Optional.of(joueur));

        // When
        joueurComponent.deleteJoueur(id);

        // Then
        verify(joueurRepository, times(1)).findById(id);
        verify(joueurRepository, times(1)).delete(joueur);
    }

    @Test
    void testDeleteJoueurNotFound() {
        // Given
        Long id = 1L;
        when(joueurRepository.findById(id)).thenReturn(Optional.empty());

        // When/Then
        assertThrows(NotFoundJoueurEntityException.class, () -> joueurComponent.deleteJoueur(id));
        verify(joueurRepository, times(1)).findById(id);
        verify(joueurRepository, times(0)).delete(any(JoueurEntity.class));
    }
}