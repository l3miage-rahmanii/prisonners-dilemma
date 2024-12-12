package fr.uga.l3miage.pc.components;

import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.pc.repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientComponent {

    private final ClientRepository clientRepository;
    private String clientNonTrouve = "Client introuvable";

    public ClientEntity getClientById(Long id) throws NotFoundClientEntityException {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundClientEntityException(clientNonTrouve));
    }

    public ClientEntity updateAdresse(Long id, String nouvelleAdresse) throws NotFoundClientEntityException, BadRequestException {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new NotFoundClientEntityException(clientNonTrouve));
        if (nouvelleAdresse == null || nouvelleAdresse.isEmpty()) {
            throw new BadRequestException("L'adresse ne peut pas être vide");
        }
        client.setAdresse(nouvelleAdresse);
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) throws NotFoundClientEntityException {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new NotFoundClientEntityException(clientNonTrouve));
        clientRepository.delete(client);
    }
}