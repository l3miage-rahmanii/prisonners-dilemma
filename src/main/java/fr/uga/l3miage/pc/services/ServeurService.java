package fr.uga.l3miage.pc.services;

import fr.uga.l3miage.pc.components.ServeurComponent;
import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.mappers.ServeurMapper;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServeurService {

    private static final String SERVER_NOT_FOUND_MESSAGE = "Serveur non trouve";

    private final ServeurMapper serveurMapper;
    private final ServeurComponent serveurComponent;
    private final ServeurRepository serveurRepository;

    public ServeurEntity getServeurEntityById(Long id) {
        return serveurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(SERVER_NOT_FOUND_MESSAGE));
    }

    public ServeurResponseDTO getServeurById(Long id) {
        try {
            ServeurEntity serveur = serveurComponent.getServeurById(id);
            return serveurMapper.toResponse(serveur);
        } catch (NotFoundServeurEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

    public ServeurResponseDTO updateServeurStatus(Long id, String nouveauStatus) {
        try {
            ServeurEntity serveur = serveurComponent.updateStatus(id, nouveauStatus);
            return serveurMapper.toResponse(serveur);
        } catch (NotFoundServeurEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public ServeurResponseDTO creerServeur(ServeurRequestDTO request) {
        ServeurEntity serveur = ServeurEntity.builder()
                .status(request.getStatus())
                .adresse(request.getAdresse())
                .build();

        serveurRepository.save(serveur);

        return ServeurResponseDTO.builder()
                .id(serveur.getId())
                .status(serveur.getStatus())
                .adresse(serveur.getAdresse())
                .build();
    }

    public void supprimerServeur(Long id) {
        ServeurEntity serveur = serveurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(SERVER_NOT_FOUND_MESSAGE));
        serveurRepository.delete(serveur);
    }

    public boolean isServeurDisponible(Long serveurId) {
        ServeurEntity serveur = serveurRepository.findById(serveurId)
                .orElseThrow(() -> new NotFoundEntityRestException(SERVER_NOT_FOUND_MESSAGE));
        return serveur.getStatus().equalsIgnoreCase("disponible");
    }

    public ServeurEntity reserverServeur(Long serveurId) {
        ServeurEntity serveur = serveurRepository.findById(serveurId)
                .orElseThrow(() -> new NotFoundEntityRestException(SERVER_NOT_FOUND_MESSAGE));

        if (!serveur.getStatus().equalsIgnoreCase("disponible")) {
            throw new BadRequestRestException("Le serveur n'est pas disponible");
        }

        serveur.setStatus("occupe");
        return serveurRepository.save(serveur);
    }
}
