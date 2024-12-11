package fr.uga.l3miage.pc.prisonersdilemma.Repository;

import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Repositories.JoueurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class JoueurRepositoryTest {

    @Mock
    private JoueurRepository joueurRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        JoueurEntity joueur = JoueurEntity.builder()
                .id(id)
                .nom("John Doe")
                .score(15)
                .abandon(false)
                .build();

        when(joueurRepository.findById(id)).thenReturn(Optional.of(joueur));

        // When
        Optional<JoueurEntity> foundJoueur = joueurRepository.findById(id);

        // Then
        assertThat(foundJoueur).isPresent();
        assertThat(foundJoueur.get().getNom()).isEqualTo("John Doe");
        assertThat(foundJoueur.get().getScore()).isEqualTo(15);
        assertThat(foundJoueur.get().isAbandon()).isFalse();

        verify(joueurRepository, times(1)).findById(id);
    }
}
