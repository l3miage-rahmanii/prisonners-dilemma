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

    // Créer une partie
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

    // Récupérer une partie par ID
    public PartieEntity getPartieById(Long id) {
        return partieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partie non trouvée avec l'id : " + id));
    }

    // Récupérer toutes les parties
    public List<PartieEntity> getAllParties() {
        return partieRepository.findAll();
    }

    // Mettre à jour l'abandon dans une partie
    public PartieEntity updateAbandon(Long id, boolean abandon, String strategieAbandon) {
        PartieEntity partie = getPartieById(id);
        partie.setAbandon(abandon);
        partie.setStrategieAbandon(strategieAbandon);
        return partieRepository.save(partie);
    }

    // Mettre à jour le résultat d'une partie
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
