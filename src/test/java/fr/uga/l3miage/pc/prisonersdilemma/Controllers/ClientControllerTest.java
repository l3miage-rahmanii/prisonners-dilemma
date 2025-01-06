package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.ClientController;
import fr.uga.l3miage.pc.requests.ClientRequestDTO;
import fr.uga.l3miage.pc.responses.ClientResponseDTO;
import fr.uga.l3miage.pc.services.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    public ClientControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreerClient() {
        // Given
        ClientRequestDTO request = new ClientRequestDTO();
        request.setNom("Test Client");

        ClientResponseDTO response = new ClientResponseDTO();
        response.setNom("Test Client");

        when(clientService.creerClient(any(ClientRequestDTO.class))).thenReturn(response);

        // When
        ResponseEntity<ClientResponseDTO> result = clientController.creerClient(request);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Test Client", result.getBody().getNom());
    }

    @Test
    void testObtenirClient() {
        // Given
        Long id = 1L;
        ClientResponseDTO response = new ClientResponseDTO();
        response.setId(id);
        response.setNom("Test Client");

        when(clientService.getClientById(id)).thenReturn(response);

        // When
        ResponseEntity<ClientResponseDTO> result = clientController.obtenirClient(id);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals(id, result.getBody().getId());
        assertEquals("Test Client", result.getBody().getNom());
    }
}