package fr.uga.l3miage.pc.Components;


import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.Repositories.ServeurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServeurComponent {

    private final ServeurRepository serveurRepository;

    public ServeurEntity getServeurById(Long id) throws NotFoundServeurEntityException {
        return serveurRepository.findById(id).orElseThrow(() -> new NotFoundServeurEntityException("Serveur introuvable"));
    }

    public ServeurEntity updateStatus(Long id, String nouveauStatus) throws NotFoundServeurEntityException, BadRequestException {
        ServeurEntity serveur = serveurRepository.findById(id).orElseThrow(() -> new NotFoundServeurEntityException("Serveur introuvable"));
        if (nouveauStatus == null || nouveauStatus.isEmpty()) {
            throw new BadRequestException("Le statut ne peut pas être vide");
        }
        serveur.setStatus(nouveauStatus);
        return serveurRepository.save(serveur);
    }

    public void deleteServeur(Long id) throws NotFoundServeurEntityException {
        ServeurEntity serveur = serveurRepository.findById(id).orElseThrow(() -> new NotFoundServeurEntityException("Serveur introuvable"));
        serveurRepository.delete(serveur);
    }
}
