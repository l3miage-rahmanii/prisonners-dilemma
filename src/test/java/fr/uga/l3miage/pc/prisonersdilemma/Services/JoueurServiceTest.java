package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.JoueurComponent;
import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.mappers.JoueurMapper;
import fr.uga.l3miage.pc.repositories.JoueurRepository;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import fr.uga.l3miage.pc.services.JoueurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    void testUpdateScoreSuccess() throws Exception {
        // Given
        Long joueurId = 1L;
        int nouveauScore = 100;
        JoueurEntity joueurEntity = new JoueurEntity();
        joueurEntity.setId(joueurId);
        joueurEntity.setScore(nouveauScore);

        JoueurResponseDTO joueurResponseDTO = JoueurResponseDTO.builder()
                .id(joueurId)
                .score(nouveauScore)
                .build();

        when(joueurComponent.updateScore(joueurId, nouveauScore)).thenReturn(joueurEntity);
        when(joueurMapper.toResponse(joueurEntity)).thenReturn(joueurResponseDTO);

        // When
        JoueurResponseDTO result = joueurService.updateScore(joueurId, nouveauScore);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getScore()).isEqualTo(nouveauScore);
        verify(joueurComponent, times(1)).updateScore(joueurId, nouveauScore);
        verify(joueurMapper, times(1)).toResponse(joueurEntity);
    }

    @Test
    void testUpdateScoreBadRequest() throws Exception {
        // Given
        Long joueurId = 1L;
        int nouveauScore = -10;

        when(joueurComponent.updateScore(joueurId, nouveauScore)).thenThrow(new BadRequestRestException("Score invalide"));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> joueurService.updateScore(joueurId, nouveauScore));
        verify(joueurComponent, times(1)).updateScore(joueurId, nouveauScore);
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