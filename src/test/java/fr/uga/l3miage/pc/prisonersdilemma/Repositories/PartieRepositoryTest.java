package fr.uga.l3miage.pc.prisonersdilemma.Repositories;

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class PartieRepositoryTest {

    @Mock
    private PartieRepository partieRepository;

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
                .build();

        when(partieRepository.findById(id)).thenReturn(Optional.of(partie));

        // When
        Optional<PartieEntity> foundPartie = partieRepository.findById(id);

        // Then
        assertThat(foundPartie).isPresent();
        assertThat(foundPartie.get().getStatus()).isEqualTo("Terminée");

        verify(partieRepository, times(1)).findById(id);
    }
}
