package fr.uga.l3miage.pc.components;

import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.Technical.NotFoundPartieEntityException;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PartieComponent {

    private final PartieRepository partieRepository;
    private String partieNonTrouve = "Partie introuvable";

    public PartieEntity getPartieById(Long id) throws NotFoundPartieEntityException {
        return partieRepository.findById(id).orElseThrow(() -> new NotFoundPartieEntityException(partieNonTrouve));
    }

    public PartieEntity updateStatus(Long id, String nouveauStatus) throws NotFoundPartieEntityException, BadRequestException {
        PartieEntity partie = partieRepository.findById(id).orElseThrow(() -> new NotFoundPartieEntityException(partieNonTrouve));
        if (nouveauStatus == null || nouveauStatus.isEmpty()) {
            throw new BadRequestException("Le statut ne peut pas être vide");
        }
        partie.setStatus(nouveauStatus);
        return partieRepository.save(partie);
    }

    public void deletePartie(Long id) throws NotFoundPartieEntityException {
        PartieEntity partie = partieRepository.findById(id).orElseThrow(() -> new NotFoundPartieEntityException(partieNonTrouve));
        partieRepository.delete(partie);
    }
}