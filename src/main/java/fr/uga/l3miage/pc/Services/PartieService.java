package fr.uga.l3miage.pc.Services;

import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Repositories.PartieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PartieService {

    private final PartieRepository partieRepository;

    // Cr�er une partie
    public PartieEntity createPartie(JoueurEntity client1, JoueurEntity client2, int nombreTours) {
        PartieEntity partie = PartieEntity.builder()
                .client1(client1)
                .client2(client2)
                .nombreTours(nombreTours)
                .abandon(false)
                .resultat(null)
                .build();
        return partieRepository.save(partie);
    }

    // R�cup�rer une partie par ID
    public PartieEntity getPartieById(Long id) {
        return partieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partie non trouv�e avec l'id : " + id));
    }

    // R�cup�rer toutes les parties
    public List<PartieEntity> getAllParties() {
        return partieRepository.findAll();
    }

    // Mettre � jour l'abandon dans une partie
    public PartieEntity updateAbandon(Long id, boolean abandon, String strategieAbandon) {
        PartieEntity partie = getPartieById(id);
        partie.setAbandon(abandon);
        partie.setStrategieAbandon(strategieAbandon);
        return partieRepository.save(partie);
    }

    // Mettre � jour le r�sultat d'une partie
    public PartieEntity updateResultat(Long id, String resultat) {
        PartieEntity partie = getPartieById(id);
        partie.setResultat(resultat);
        return partieRepository.save(partie);
    }

    // Supprimer une partie
    public void deletePartie(Long id) {
        partieRepository.deleteById(id);
    }
}
