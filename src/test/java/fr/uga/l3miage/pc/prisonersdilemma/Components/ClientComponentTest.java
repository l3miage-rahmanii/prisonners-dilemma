package fr.uga.l3miage.pc.prisonersdilemma.Component;


import fr.uga.l3miage.pc.Components.ClientComponent;
import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundClientEntityException;
import fr.uga.l3miage.pc.Repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class ClientComponentTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientComponent clientComponent;

    public ClientComponentTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetClientByIdSuccess() throws NotFoundClientEntityException {
        Long id = 1L;
        ClientEntity client = new ClientEntity();
        client.setId(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        ClientEntity result = clientComponent.getClientById(id);

        assertEquals(client, result);
    }

    @Test
    void testGetClientByIdNotFound() {
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundClientEntityException.class, () -> clientComponent.getClientById(id));
    }

    /*
    @Test
    void testUpdateAdresseSuccess() throws NotFoundClientEntityException, BadRequestException {
        Long id = 1L;
        String nouvelleAdresse = "New Address";
        ClientEntity client = new ClientEntity();
        client.setId(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        ClientEntity result = clientComponent.updateAdresse(id, nouvelleAdresse);

        assertEquals(nouvelleAdresse, result.getAdresse());
    }

     */

    @Test
    void testUpdateAdresseInvalid() {
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.of(new ClientEntity()));

        assertThrows(BadRequestException.class, () -> clientComponent.updateAdresse(id, ""));
    }

    @Test
    void testDeleteClientSuccess() throws NotFoundClientEntityException {
        Long id = 1L;
        ClientEntity client = new ClientEntity();
        client.setId(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));

        clientComponent.deleteClient(id);

        verify(clientRepository, times(1)).delete(client);
    }

    @Test
    void testDeleteClientNotFound() {
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundClientEntityException.class, () -> clientComponent.deleteClient(id));
    }
}