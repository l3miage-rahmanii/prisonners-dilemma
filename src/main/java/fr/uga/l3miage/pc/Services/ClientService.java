package fr.uga.l3miage.pc.Services;


import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.Repositories.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<ClientEntity> getAllClients() {
        return clientRepository.findAll();
    }

    public ClientEntity getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }

    public ClientEntity createClient(ClientEntity clientEntity) {
        return clientRepository.save(clientEntity);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
