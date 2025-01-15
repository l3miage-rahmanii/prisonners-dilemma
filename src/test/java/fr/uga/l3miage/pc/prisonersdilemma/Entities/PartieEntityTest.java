package fr.uga.l3miage.pc.prisonersdilemma.Entities;

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PartieEntityTest {

    @Test
    void testSetStatus() {
        PartieEntity partie = new PartieEntity();
        partie.setStatus("en cours");
        assertEquals("en cours", partie.getStatus());
    }

    @Test
    void testSetNbTours() {
        PartieEntity partie = new PartieEntity();
        partie.setNbTours(5);
        assertEquals(5, partie.getNbTours());
    }

    @Test
    void testSetIdJoueur1() {
        PartieEntity partie = new PartieEntity();
        partie.setIdJoueur1(1);
        assertEquals(1, partie.getIdJoueur1());
    }

    @Test
    void testSetIdJoueur2() {
        PartieEntity partie = new PartieEntity();
        partie.setIdJoueur2(2);
        assertEquals(2, partie.getIdJoueur2());
    }

    @Test
    void testSetCoupsJoueur1() {
        PartieEntity partie = new PartieEntity();
        partie.setCoupsJoueur1(Arrays.asList(CoupEnum.COOPERER, CoupEnum.TRAHIR));
        assertEquals(Arrays.asList(CoupEnum.COOPERER, CoupEnum.TRAHIR), partie.getCoupsJoueur1());
    }

    @Test
    void testSetCoupsJoueur2() {
        PartieEntity partie = new PartieEntity();
        partie.setCoupsJoueur2(Arrays.asList(CoupEnum.TRAHIR, CoupEnum.COOPERER));
        assertEquals(Arrays.asList(CoupEnum.TRAHIR, CoupEnum.COOPERER), partie.getCoupsJoueur2());
    }

    @Test
    void testSetScoreJoueur1() {
        PartieEntity partie = new PartieEntity();
        partie.setScoreJoueur1(10);
        assertEquals(10, partie.getScoreJoueur1());
    }

    @Test
    void testSetScoreJoueur2() {
        PartieEntity partie = new PartieEntity();
        partie.setScoreJoueur2(8);
        assertEquals(8, partie.getScoreJoueur2());
    }

    @Test
    void testSetStrategieJoueur() {
        PartieEntity partie = new PartieEntity();
        partie.setStrategieJoueur(StrategieEnum.TOUJOURS_COOPERER);
        assertEquals(StrategieEnum.TOUJOURS_COOPERER, partie.getStrategieJoueur());
    }
}