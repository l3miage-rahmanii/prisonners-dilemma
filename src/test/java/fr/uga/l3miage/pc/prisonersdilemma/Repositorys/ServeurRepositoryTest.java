package fr.uga.l3miage.pc.prisonersdilemma.Repositorys;

import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ServeurRepositoryTest {

    @Mock
    private ServeurRepository serveurRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        ServeurEntity serveur = ServeurEntity.builder()
                .id(id)
                .status("Maintenance")
                .build();

        when(serveurRepository.findById(id)).thenReturn(Optional.of(serveur));

        // When
        Optional<ServeurEntity> foundServeur = serveurRepository.findById(id);

        // Then
        assertThat(foundServeur).isPresent();
        assertThat(foundServeur.get().getStatus()).isEqualTo("Maintenance");

        verify(serveurRepository, times(1)).findById(id);
    }
}