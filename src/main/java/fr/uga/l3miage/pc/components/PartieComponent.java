package fr.uga.l3miage.pc.components;

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundPartieEntityException;
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

    public boolean isPartieActive(Long id) throws NotFoundPartieEntityException {
        PartieEntity partie = getPartieById(id);
        return "en_cours".equals(partie.getStatus());
    }

    public PartieEntity reinitialiserPartie(Long id) throws NotFoundPartieEntityException {
        PartieEntity partie = getPartieById(id);
        partie.setStatus("en_attente");
        partie.setScoreJoueur1(0);
        partie.setScoreJoueur2(0);
        partie.getCoupsJoueur1().clear();
        partie.getCoupsJoueur2().clear();
        return partieRepository.save(partie);
    }

    public boolean verifierCoupValide(String coup) {
        return coup != null && (coup.equals("c") || coup.equals("t"));
    }

    public boolean jouerPeutRejoindre(Long partieId, Long joueurId) {
        return !partieRepository.findByIdAndJoueursId(partieId, joueurId).isPresent();
    }
}