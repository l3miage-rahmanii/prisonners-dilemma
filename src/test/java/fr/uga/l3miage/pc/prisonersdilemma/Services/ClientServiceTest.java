package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.ClientComponent;
import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.exceptions.Rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.Rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.Mappers.ClientMapper;
import fr.uga.l3miage.pc.repositories.ClientRepository;
import fr.uga.l3miage.pc.requests.ClientRequestDTO;
import fr.uga.l3miage.pc.responses.ClientResponseDTO;
import fr.uga.l3miage.pc.Services.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ClientServiceTest {

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private ClientComponent clientComponent;

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientByIdSuccess() throws Exception {
        // Given
        Long clientId = 1L;
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(clientId);
        clientEntity.setNom("Test Client");
        clientEntity.setAdresse("Test Adresse");

        ClientResponseDTO clientResponseDTO = ClientResponseDTO.builder()
                .id(clientId)
                .nom("Test Client")
                .adresse("Test Adresse")
                .build();

        when(clientComponent.getClientById(clientId)).thenReturn(clientEntity);
        when(clientMapper.toResponse(clientEntity)).thenReturn(clientResponseDTO);

        // When
        ClientResponseDTO result = clientService.getClientById(clientId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Test Client");
        verify(clientComponent, times(1)).getClientById(clientId);
        verify(clientMapper, times(1)).toResponse(clientEntity);
    }

    @Test
    void testGetClientByIdNotFound() throws Exception {
        // Given
        Long clientId = 1L;
        when(clientComponent.getClientById(clientId)).thenThrow(new NotFoundEntityRestException("Client non trouvé"));

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> clientService.getClientById(clientId));
        verify(clientComponent, times(1)).getClientById(clientId);
    }

    @Test
    void testUpdateClientAdresseSuccess() throws Exception {
        // Given
        Long clientId = 1L;
        String nouvelleAdresse = "Nouvelle Adresse";
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(clientId);
        clientEntity.setAdresse(nouvelleAdresse);

        ClientResponseDTO clientResponseDTO = ClientResponseDTO.builder()
                .id(clientId)
                .adresse(nouvelleAdresse)
                .build();

        when(clientComponent.updateAdresse(clientId, nouvelleAdresse)).thenReturn(clientEntity);
        when(clientMapper.toResponse(clientEntity)).thenReturn(clientResponseDTO);

        // When
        ClientResponseDTO result = clientService.updateClientAdresse(clientId, nouvelleAdresse);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getAdresse()).isEqualTo(nouvelleAdresse);
        verify(clientComponent, times(1)).updateAdresse(clientId, nouvelleAdresse);
        verify(clientMapper, times(1)).toResponse(clientEntity);
    }

    @Test
    void testUpdateClientAdresseBadRequest() throws Exception {
        // Given
        Long clientId = 1L;
        String nouvelleAdresse = "";

        when(clientComponent.updateAdresse(clientId, nouvelleAdresse)).thenThrow(new BadRequestRestException("Adresse invalide"));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> clientService.updateClientAdresse(clientId, nouvelleAdresse));
        verify(clientComponent, times(1)).updateAdresse(clientId, nouvelleAdresse);
    }

    @Test
    void testCreerClient() {
        // Given
        ClientRequestDTO request = new ClientRequestDTO();
        request.setNom("Nouveau Client");
        request.setAdresse("Nouvelle Adresse");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setNom("Nouveau Client");
        clientEntity.setAdresse("Nouvelle Adresse");

        when(clientRepository.save(any(ClientEntity.class))).thenReturn(clientEntity);

        ClientResponseDTO clientResponseDTO = ClientResponseDTO.builder()
                .nom("Nouveau Client")
                .adresse("Nouvelle Adresse")
                .build();

        when(clientMapper.toResponse(clientEntity)).thenReturn(clientResponseDTO);

        // When
        ClientResponseDTO result = clientService.creerClient(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Nouveau Client");
        verify(clientRepository, times(1)).save(any(ClientEntity.class));
    }

    @Test
    void testDemarrerPartieClientNonTrouve() {
        // Given
        Long clientId = 1L;

        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> clientService.demarrerPartie(clientId));
        verify(clientRepository, times(1)).findById(clientId);
    }
}
