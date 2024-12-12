package fr.uga.l3miage.pc.prisonersdilemma.Components;


import fr.uga.l3miage.pc.components.ClientComponent;
import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.pc.repositories.ClientRepository;
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

class ClientComponentTest {

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
    void testGetClientByIdWithInvalidId() {
        Long invalidId = null;

        assertThrows(NotFoundClientEntityException.class, () -> clientComponent.getClientById(invalidId));
    }

    @Test
    void testUpdateAdresseWithInvalidId() {
        Long invalidId = null;
        String nouvelleAdresse = "Nouvelle Adresse";

        assertThrows(NotFoundClientEntityException.class, () -> clientComponent.updateAdresse(invalidId, nouvelleAdresse));
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

    @Test
    void testUpdateAdresseClientNotFound() {
        Long id = 1L;
        String nouvelleAdresse = "Nouvelle Adresse";
        when(clientRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundClientEntityException.class, () -> clientComponent.updateAdresse(id, nouvelleAdresse));
    }

    @Test
    void testUpdateAdresseNullAdresse() {
        Long id = 1L;
        when(clientRepository.findById(id)).thenReturn(Optional.of(new ClientEntity()));

        assertThrows(BadRequestException.class, () -> clientComponent.updateAdresse(id, null));
    }

    @Test
    void testUpdateAdresseSaveFailure() throws NotFoundClientEntityException {
        Long id = 1L;
        String nouvelleAdresse = "Nouvelle Adresse";
        ClientEntity client = new ClientEntity();
        client.setId(id);
        when(clientRepository.findById(id)).thenReturn(Optional.of(client));
        when(clientRepository.save(client)).thenThrow(new RuntimeException("Échec de la sauvegarde"));

        assertThrows(RuntimeException.class, () -> clientComponent.updateAdresse(id, nouvelleAdresse));
    }



}