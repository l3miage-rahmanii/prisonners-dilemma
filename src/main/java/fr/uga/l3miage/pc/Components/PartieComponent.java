package fr.uga.l3miage.pc.Components;

import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundPartieEntityException;
import fr.uga.l3miage.pc.Repositories.PartieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PartieComponent {

    private final PartieRepository partieRepository;

    public PartieEntity getPartieById(Long id) throws NotFoundPartieEntityException {
        return partieRepository.findById(id).orElseThrow(() -> new NotFoundPartieEntityException("Partie introuvable"));
    }

    public PartieEntity updateStatus(Long id, String nouveauStatus) throws NotFoundPartieEntityException, BadRequestException {
        PartieEntity partie = partieRepository.findById(id).orElseThrow(() -> new NotFoundPartieEntityException("Partie introuvable"));
        if (nouveauStatus == null || nouveauStatus.isEmpty()) {
            throw new BadRequestException("Le statut ne peut pas être vide");
        }
        partie.setStatus(nouveauStatus);
        return partieRepository.save(partie);
    }

    public void deletePartie(Long id) throws NotFoundPartieEntityException {
        PartieEntity partie = partieRepository.findById(id).orElseThrow(() -> new NotFoundPartieEntityException("Partie introuvable"));
        partieRepository.delete(partie);
    }
}