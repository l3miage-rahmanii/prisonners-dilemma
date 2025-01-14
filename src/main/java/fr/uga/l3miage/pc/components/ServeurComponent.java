package fr.uga.l3miage.pc.components;


import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServeurComponent {

    private final ServeurRepository serveurRepository;
    private String serveurNonTrouve = "Serveur introuvable";

    public ServeurEntity getServeurById(Long id) throws NotFoundServeurEntityException {
        return serveurRepository.findById(id).orElseThrow(() -> new NotFoundServeurEntityException(serveurNonTrouve));
    }

    public ServeurEntity updateStatus(Long id, String nouveauStatus) throws NotFoundServeurEntityException, BadRequestException {
        ServeurEntity serveur = serveurRepository.findById(id).orElseThrow(() -> new NotFoundServeurEntityException(serveurNonTrouve));
        if (nouveauStatus == null || nouveauStatus.isEmpty()) {
            throw new BadRequestException("Le statut ne peut pas être vide");
        }
        serveur.setStatus(nouveauStatus);
        return serveurRepository.save(serveur);
    }

    public void deleteServeur(Long id) throws NotFoundServeurEntityException {
        ServeurEntity serveur = serveurRepository.findById(id).orElseThrow(() -> new NotFoundServeurEntityException(serveurNonTrouve));
        serveurRepository.delete(serveur);
    }
}
