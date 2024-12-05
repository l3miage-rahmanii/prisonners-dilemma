package fr.uga.l3miage.pc.Components;

import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Services.PartieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PartieComponent {

    private final PartieService partieService;

    // Exemple : Créer une partie avec un résultat par défaut
    public PartieEntity creerPartieAvecResultat(JoueurEntity client1, JoueurEntity client2, int nombreTours, String resultat) {
        PartieEntity partie = partieService.createPartie(client1, client2, nombreTours);
        partie.setResultat(resultat);
        return partieService.updateResultat(partie.getId(), resultat);
    }
}
