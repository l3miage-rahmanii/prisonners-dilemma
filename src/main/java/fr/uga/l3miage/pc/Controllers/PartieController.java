package fr.uga.l3miage.pc.Controllers;

import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Services.PartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PartieController {

    private final PartieService partieService;

    // Cr�e une nouvelle partie
    public PartieEntity createPartie(JoueurEntity client1, JoueurEntity client2, int nombreTours) {
        return partieService.createPartie(client1, client2, nombreTours);
    }

    // R�cup�re une partie par ID
    public PartieEntity getPartieById(Long id) {
        return partieService.getPartieById(id);
    }

    // R�cup�re toutes les parties
    public List<PartieEntity> getAllParties() {
        return partieService.getAllParties();
    }

    // Met � jour l'abandon
    public PartieEntity updateAbandon(Long id, boolean abandon, String strategieAbandon) {
        return partieService.updateAbandon(id, abandon, strategieAbandon);
    }

    // Met � jour le r�sultat
    public PartieEntity updateResultat(Long id, String resultat) {
        return partieService.updateResultat(id, resultat);
    }

    // Supprime une partie
    public void deletePartie(Long id) {
        partieService.deletePartie(id);
    }
}

