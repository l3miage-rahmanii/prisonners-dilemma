package fr.uga.l3miage.pc.prisonersdilemma.Repositories;

import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class PartieRepositoryTest {
    @Mock
    private PartieRepository partieRepository;

    @Mock
    private ServeurEntity serveurEntity;

    @Mock
    private JoueurEntity joueurEntity1;

    @Mock
    private JoueurEntity joueurEntity2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        PartieEntity partie = PartieEntity.builder()
                .id(id)
                .status("Terminée")
                .serveur(serveurEntity)
                .joueurs(List.of(joueurEntity1, joueurEntity2))
                .scoreJoueur1(10)
                .scoreJoueur2(8)
                .strategieJoueur1(StrategieEnum.TOUJOURS_COOPERER)
                .strategieJoueur2(StrategieEnum.TOUJOURS_TRAHIR)
                .build();

        when(partieRepository.findById(id)).thenReturn(Optional.of(partie));

        // When
        Optional<PartieEntity> foundPartie = partieRepository.findById(id);

        // Then
        assertThat(foundPartie).isPresent();
        assertThat(foundPartie.get().getStatus()).isEqualTo("Terminée");
        assertThat(foundPartie.get().getServeur()).isEqualTo(serveurEntity);
        assertThat(foundPartie.get().getJoueurs()).hasSize(2);
        assertThat(foundPartie.get().getScoreJoueur1()).isEqualTo(10);
        assertThat(foundPartie.get().getScoreJoueur2()).isEqualTo(8);

        verify(partieRepository, times(1)).findById(id);
    }

    @Test
    void testFindByServeurId() {
        // Given
        Long serveurId = 1L;
        PartieEntity partie1 = PartieEntity.builder()
                .id(1L)
                .status("En cours")
                .serveur(serveurEntity)
                .joueurs(List.of(joueurEntity1))
                .scoreJoueur1(5)
                .scoreJoueur2(7)
                .strategieJoueur1(StrategieEnum.TOUJOURS_COOPERER)
                .strategieJoueur2(StrategieEnum.TOUJOURS_TRAHIR)
                .build();
        PartieEntity partie2 = PartieEntity.builder()
                .id(2L)
                .status("Terminée")
                .serveur(serveurEntity)
                .joueurs(List.of(joueurEntity2))
                .scoreJoueur1(9)
                .scoreJoueur2(10)
                .strategieJoueur1(StrategieEnum.TOUJOURS_COOPERER)
                .strategieJoueur2(StrategieEnum.TOUJOURS_TRAHIR)
                .build();

        when(partieRepository.findByServeurId(serveurId)).thenReturn(List.of(partie1, partie2));

        // When
        List<PartieEntity> foundParties = partieRepository.findByServeurId(serveurId);

        // Then
        assertThat(foundParties).hasSize(2);
        assertThat(foundParties.get(0).getServeur()).isEqualTo(serveurEntity);
        assertThat(foundParties.get(1).getServeur()).isEqualTo(serveurEntity);
        assertThat(foundParties.get(0).getJoueurs()).hasSize(1);
        assertThat(foundParties.get(1).getJoueurs()).hasSize(1);

        verify(partieRepository, times(1)).findByServeurId(serveurId);
    }

    @Test
    void testFindByJoueursId() {
        // Given
        Long joueurId = 1L;
        PartieEntity partie1 = PartieEntity.builder()
                .id(1L)
                .status("En cours")
                .serveur(serveurEntity)
                .joueurs(List.of(joueurEntity1))
                .scoreJoueur1(5)
                .scoreJoueur2(7)
                .strategieJoueur1(StrategieEnum.TOUJOURS_COOPERER)
                .strategieJoueur2(StrategieEnum.TOUJOURS_TRAHIR)
                .build();
        PartieEntity partie2 = PartieEntity.builder()
                .id(2L)
                .status("Terminée")
                .serveur(serveurEntity)
                .joueurs(List.of(joueurEntity1))
                .scoreJoueur1(9)
                .scoreJoueur2(10)
                .strategieJoueur1(StrategieEnum.TOUJOURS_COOPERER)
                .strategieJoueur2(StrategieEnum.TOUJOURS_TRAHIR)
                .build();

        when(partieRepository.findByJoueursId(joueurId)).thenReturn(List.of(partie1, partie2));

        // When
        List<PartieEntity> foundParties = partieRepository.findByJoueursId(joueurId);

        // Then
        assertThat(foundParties).hasSize(2);
        assertThat(foundParties.get(0).getJoueurs()).contains(joueurEntity1);
        assertThat(foundParties.get(1).getJoueurs()).contains(joueurEntity1);

        verify(partieRepository, times(1)).findByJoueursId(joueurId);
    }

    @Test
    void testFindByIdAndJoueursId() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        Object StrategieEnum;
        PartieEntity partie = PartieEntity.builder()
                .id(partieId)
                .status("Terminée")
                .serveur(serveurEntity)
                .joueurs(List.of(joueurEntity1))
                .scoreJoueur1(5)
                .scoreJoueur2(7)
                .strategieJoueur1(fr.uga.l3miage.pc.enums.StrategieEnum.TOUJOURS_COOPERER)
                .strategieJoueur2(fr.uga.l3miage.pc.enums.StrategieEnum.TOUJOURS_TRAHIR)
                .build();

        when(partieRepository.findByIdAndJoueursId(partieId, joueurId)).thenReturn(Optional.of(partie));

        // When
        Optional<PartieEntity> foundPartie = partieRepository.findByIdAndJoueursId(partieId, joueurId);

        // Then
        assertThat(foundPartie).isPresent();
        assertThat(foundPartie.get().getId()).isEqualTo(partieId);
        assertThat(foundPartie.get().getJoueurs()).contains(joueurEntity1);

        verify(partieRepository, times(1)).findByIdAndJoueursId(partieId, joueurId);
    }
}