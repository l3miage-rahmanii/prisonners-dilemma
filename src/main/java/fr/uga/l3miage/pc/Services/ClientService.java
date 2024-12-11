package fr.uga.l3miage.pc.Services;


import fr.uga.l3miage.pc.Components.ClientComponent;
import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.Exceptions.Rest.BadRequestRestException;
import fr.uga.l3miage.pc.Exceptions.Rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundClientEntityException;
import fr.uga.l3miage.pc.Mappers.ClientMapper;
import fr.uga.l3miage.pc.Repositories.ClientRepository;
import fr.uga.l3miage.pc.Requests.ClientRequestDTO;
import fr.uga.l3miage.pc.Responses.ClientResponseDTO;
import lombok.*;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    private final ClientComponent clientComponent;
    private final ClientRepository clientRepository;

    public ClientResponseDTO getClientById(Long id) {
        try {
            ClientEntity client = clientComponent.getClientById(id);
            return clientMapper.toResponse(client);
        } catch (NotFoundClientEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

    public ClientResponseDTO updateClientAdresse(Long id, String nouvelleAdresse) {
        try {
            ClientEntity client = clientComponent.updateAdresse(id, nouvelleAdresse);
            return clientMapper.toResponse(client);
        } catch (NotFoundClientEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public ClientResponseDTO creerClient(ClientRequestDTO request) {
        // Logique métier pour créer un client
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setNom(request.getNom());
        clientEntity.setAdresse(request.getAdresse());
        clientRepository.save(clientEntity);

        return ClientResponseDTO.builder()
                .id(clientEntity.getId())
                .nom(clientEntity.getNom())
                .adresse(clientEntity.getAdresse())
                .build();
    }

    public void demarrerPartie(Long clientId) {
        try {
            // Logique pour démarrer une partie (vérifications, préparation, etc.)
            ClientEntity client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new NotFoundClientEntityException("Client non trouvé"));
        } catch (NotFoundClientEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }
}