package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.JoueurComponent;
import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.mappers.JoueurMapper;
import fr.uga.l3miage.pc.repositories.JoueurRepository;
import fr.uga.l3miage.pc.requests.JoueurRequestDTO;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import fr.uga.l3miage.pc.services.JoueurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class JoueurServiceTest {

    @Mock
    private JoueurMapper joueurMapper;

    @Mock
    private JoueurComponent joueurComponent;

    @Mock
    private JoueurRepository joueurRepository;

    @InjectMocks
    private JoueurService joueurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetJoueurByIdSuccess() throws Exception {
        // Given
        Long joueurId = 1L;
        JoueurEntity joueurEntity = new JoueurEntity();
        joueurEntity.setId(joueurId);
        joueurEntity.setNom("Test Joueur");

        JoueurResponseDTO joueurResponseDTO = JoueurResponseDTO.builder()
                .id(joueurId)
                .nom("Test Joueur")
                .build();

        when(joueurComponent.getJoueurById(joueurId)).thenReturn(joueurEntity);
        when(joueurMapper.toResponse(joueurEntity)).thenReturn(joueurResponseDTO);

        // When
        JoueurResponseDTO result = joueurService.getJoueurById(joueurId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Test Joueur");
        verify(joueurComponent, times(1)).getJoueurById(joueurId);
        verify(joueurMapper, times(1)).toResponse(joueurEntity);
    }

    @Test
    void testGetJoueurByIdNotFound() throws Exception {
        // Given
        Long joueurId = 1L;
        when(joueurComponent.getJoueurById(joueurId)).thenThrow(new NotFoundEntityRestException("Joueur introuvable"));

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> joueurService.getJoueurById(joueurId));
        verify(joueurComponent, times(1)).getJoueurById(joueurId);
    }

    @Test
    void updateScoreSuccess() {
        // Arrange
        Long id = 1L;
        int initialScore = 100;
        int pointsToAdd = 50;

        JoueurEntity existingJoueur = JoueurEntity.builder()
                .id(id)
                .nom("Test Player")
                .score(initialScore)
                .build();

        JoueurEntity expectedJoueur = JoueurEntity.builder()
                .id(id)
                .nom("Test Player")
                .score(initialScore + pointsToAdd)
                .build();

        when(joueurRepository.findById(id)).thenReturn(Optional.of(existingJoueur));
        when(joueurRepository.save(any(JoueurEntity.class))).thenReturn(expectedJoueur);

        // Act
        JoueurEntity result = joueurService.updateScore(id, pointsToAdd);

        // Assert
        assertNotNull(result);
        assertEquals(initialScore + pointsToAdd, result.getScore());
        verify(joueurRepository).findById(id);
        verify(joueurRepository).save(argThat(joueur ->
                joueur.getId().equals(id) &&
                        joueur.getScore() == initialScore + pointsToAdd
        ));
    }

    @Test
    void updateScoreWithNegativePoints() {
        // Arrange
        Long id = 1L;
        int initialScore = 100;
        int pointsToSubtract = -30;

        JoueurEntity existingJoueur = JoueurEntity.builder()
                .id(id)
                .nom("Test Player")
                .score(initialScore)
                .build();

        JoueurEntity expectedJoueur = JoueurEntity.builder()
                .id(id)
                .nom("Test Player")
                .score(initialScore + pointsToSubtract)
                .build();

        when(joueurRepository.findById(id)).thenReturn(Optional.of(existingJoueur));
        when(joueurRepository.save(any(JoueurEntity.class))).thenReturn(expectedJoueur);

        // Act
        JoueurEntity result = joueurService.updateScore(id, pointsToSubtract);

        // Assert
        assertNotNull(result);
        assertEquals(initialScore + pointsToSubtract, result.getScore());
        verify(joueurRepository).findById(id);
        verify(joueurRepository).save(argThat(joueur ->
                joueur.getId().equals(id) &&
                        joueur.getScore() == initialScore + pointsToSubtract
        ));
    }

    @Test
    void updateScore_PlayerNotFound() {
        // Arrange
        Long id = 1L;
        when(joueurRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundEntityRestException.class,
                () -> joueurService.updateScore(id, 50));

        verify(joueurRepository).findById(id);
        verify(joueurRepository, never()).save(any());
    }

    @Test
    void updateScore_WithZeroPoints() {
        // Arrange
        Long id = 1L;
        int initialScore = 100;
        int pointsToAdd = 0;

        JoueurEntity existingJoueur = JoueurEntity.builder()
                .id(id)
                .nom("Test Player")
                .score(initialScore)
                .build();

        when(joueurRepository.findById(id)).thenReturn(Optional.of(existingJoueur));
        when(joueurRepository.save(any(JoueurEntity.class))).thenReturn(existingJoueur);

        // Act
        JoueurEntity result = joueurService.updateScore(id, pointsToAdd);

        // Assert
        assertNotNull(result);
        assertEquals(initialScore, result.getScore());
        verify(joueurRepository).findById(id);
        verify(joueurRepository).save(argThat(joueur ->
                joueur.getId().equals(id) &&
                        joueur.getScore() == initialScore
        ));
    }

    @Test
    void testUpdateScoreNotFound() throws Exception {
        // Given
        Long joueurId = 1L;
        int nouveauScore = -10;  // Assuming this is an invalid score that should cause the player to be not found
        when(joueurRepository.findById(joueurId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> joueurService.updateScore(joueurId, nouveauScore));
        verify(joueurRepository, times(1)).findById(joueurId);
    }


    @Test
    void testGetJoueursByIdsSuccess() {
        // Given
        List<Long> ids = Arrays.asList(1L, 2L);
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);

        when(joueurRepository.findAllById(ids)).thenReturn(Arrays.asList(joueur1, joueur2));

        // When
        List<JoueurEntity> result = joueurService.getJoueursByIds(ids);

        // Then
        assertThat(result).hasSize(2);
        verify(joueurRepository, times(1)).findAllById(ids);
    }

    @Test
    void testGetJoueursByIdsNotFound() {
        // Given
        List<Long> ids = Arrays.asList(1L, 2L);
        when(joueurRepository.findAllById(ids)).thenReturn(Arrays.asList(new JoueurEntity())); // Only one found

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> joueurService.getJoueursByIds(ids));
        verify(joueurRepository, times(1)).findAllById(ids);
    }



}