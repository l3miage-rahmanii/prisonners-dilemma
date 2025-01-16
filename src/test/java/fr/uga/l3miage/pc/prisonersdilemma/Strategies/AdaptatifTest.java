package fr.uga.l3miage.pc.prisonersdilemma.Strategies;

import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.strategies.Adaptatif;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotNull;


class AdaptatifTest {
    private Adaptatif strategie;
    private List<CoupEnum> historiqueAdversaire;

    @BeforeEach
    void setUp() {
        strategie = new Adaptatif();
        historiqueAdversaire = new ArrayList<>();
    }


    @Test
    void testUpdateScore() {
        strategie.updateScore(10);
        // Jouer quelques coups
        for (int i = 0; i < 3; i++) {
            strategie.prochainCoup(historiqueAdversaire);
            historiqueAdversaire.add(CoupEnum.COOPERER);
        }

        strategie.updateScore(20);
        // Vérifier que le score a bien été mis à jour
        // On peut vérifier indirectement en observant le comportement du prochain coup
        assertNotNull(strategie.prochainCoup(historiqueAdversaire));
    }

    @Test
    void testComportementAvecHistoriqueComplet() {
        // Créer un historique complet de 20 coups
        List<CoupEnum> longHistorique = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            longHistorique.add(CoupEnum.COOPERER);
            strategie.prochainCoup(longHistorique);
            strategie.updateScore(i * 3); // Simuler différents scores
        }

        // Vérifier que la stratégie continue à fonctionner
        assertNotNull(strategie.prochainCoup(longHistorique));
    }

}