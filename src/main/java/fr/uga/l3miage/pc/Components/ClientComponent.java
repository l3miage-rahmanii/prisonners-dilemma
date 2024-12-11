package fr.uga.l3miage.pc.Components;

import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundClientEntityException;
import fr.uga.l3miage.pc.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientComponent {

    private final ClientRepository clientRepository;

    public ClientEntity getClientById(Long id) throws NotFoundClientEntityException {
        return clientRepository.findById(id).orElseThrow(() -> new NotFoundClientEntityException("Client introuvable"));
    }

    public ClientEntity updateAdresse(Long id, String nouvelleAdresse) throws NotFoundClientEntityException, BadRequestException {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new NotFoundClientEntityException("Client introuvable"));
        if (nouvelleAdresse == null || nouvelleAdresse.isEmpty()) {
            throw new BadRequestException("L'adresse ne peut pas être vide");
        }
        client.setAdresse(nouvelleAdresse);
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) throws NotFoundClientEntityException {
        ClientEntity client = clientRepository.findById(id).orElseThrow(() -> new NotFoundClientEntityException("Client introuvable"));
        clientRepository.delete(client);
    }
}