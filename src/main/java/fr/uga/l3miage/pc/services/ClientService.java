package fr.uga.l3miage.pc.services;

import fr.uga.l3miage.pc.components.ClientComponent;
import fr.uga.l3miage.pc.entities.ClientEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundClientEntityException;
import fr.uga.l3miage.pc.mappers.ClientMapper;
import fr.uga.l3miage.pc.repositories.ClientRepository;
import fr.uga.l3miage.pc.requests.ClientRequestDTO;
import fr.uga.l3miage.pc.responses.ClientResponseDTO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientMapper clientMapper;
    private final ClientComponent clientComponent;
    private final ClientRepository clientRepository;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

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
            clientRepository.findById(clientId)
                    .orElseThrow(() -> new NotFoundClientEntityException("Client non trouvé"));

            logger.info("Partie démarrée pour le client avec ID : {}", clientId);

        } catch (NotFoundClientEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }


}
