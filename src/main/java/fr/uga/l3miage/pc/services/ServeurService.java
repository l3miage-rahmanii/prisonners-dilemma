package fr.uga.l3miage.pc.services;

import fr.uga.l3miage.pc.components.ServeurComponent;
import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.Rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.Rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.Technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.Mappers.ServeurMapper;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.Responses.ServeurResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServeurService {

    private final ServeurMapper serveurMapper;
    private final ServeurComponent serveurComponent;
    private final ServeurRepository serveurRepository;

    public ServeurEntity getServeurEntityById(Long id) {
        return serveurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serveur non trouve"));
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
                .orElseThrow(() -> new RuntimeException("Serveur non trouve"));
        serveurRepository.delete(serveur);
    }

    public boolean isServeurDisponible(Long serveurId) {
        ServeurEntity serveur = serveurRepository.findById(serveurId)
                .orElseThrow(() -> new NotFoundEntityRestException("Serveur non trouve"));
        return serveur.getStatus().equalsIgnoreCase("disponible");
    }

    public ServeurEntity reserverServeur(Long serveurId) {
        ServeurEntity serveur = serveurRepository.findById(serveurId)
                .orElseThrow(() -> new NotFoundEntityRestException("Serveur non trouve"));

        if (!serveur.getStatus().equalsIgnoreCase("disponible")) {
            throw new BadRequestRestException("Le serveur n'est pas disponible");
        }

        serveur.setStatus("occupe");
        return serveurRepository.save(serveur);
    }

}