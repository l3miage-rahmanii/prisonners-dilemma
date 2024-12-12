package fr.uga.l3miage.pc.prisonersdilemma.Repositorys;

import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.repositories.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ClientRepositoryTest {

    @Mock
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        // Given
        Long id = 1L;
        ClientEntity client = ClientEntity.builder()
                .id(id)
                .nom("Test Client")
                .adresse("123 Main Street")
                .build();

        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        // When
        Optional<ClientEntity> foundClient = clientRepository.findById(id);

        // Then
        assertThat(foundClient).isPresent();
        assertThat(foundClient.get().getNom()).isEqualTo("Test Client");
        assertThat(foundClient.get().getAdresse()).isEqualTo("123 Main Street");

        verify(clientRepository, times(1)).findById(id);
    }
}
