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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServeurService {
    private final ServeurRepository serveurRepository;
    private final ServeurMapper serveurMapper;
    private final PartieService partieService;

    public List<ServeurResponseDTO> getAllServeurs() {
        return serveurRepository.findAll().stream()
                .map(serveurMapper::toResponse)
                .collect(Collectors.toList());
    }

    public ServeurResponseDTO getServeurById(Long id) {
        return serveurRepository.findById(id)
                .map(serveurMapper::toResponse)
                .orElseThrow(() -> new NotFoundEntityRestException("Serveur non trouvé"));
    }

    public ServeurResponseDTO createServeur(ServeurRequestDTO request) {
        // Vérifier si le serveur a déjà une partie active
        if (serveurRepository.existsByPartieNotNull()) {
            throw new BadRequestRestException("Un serveur avec une partie active existe déjà");
        }

        ServeurEntity serveur = serveurMapper.toEntity(request);
        return serveurMapper.toResponse(serveurRepository.save(serveur));
    }

    public ServeurResponseDTO updateServeur(Long id, ServeurRequestDTO request) {
        ServeurEntity serveur = serveurRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityRestException("Serveur non trouvé"));

        serveurMapper.updateEntityFromRequest(request, serveur);
        return serveurMapper.toResponse(serveurRepository.save(serveur));
    }

    public void deleteServeur(Long id) {
        ServeurEntity serveur = serveurRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityRestException("Serveur non trouvé"));

        if (serveur.getPartie() != null &&
                !serveur.getPartie().getStatus().equals("terminée")) {
            throw new BadRequestRestException("Impossible de supprimer un serveur avec une partie en cours");
        }

        serveurRepository.deleteById(id);
    }
}


