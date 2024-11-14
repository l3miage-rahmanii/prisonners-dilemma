package fr.uga.l3miage.pc.prisonersdilemma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class JoueurTest {

    private Joueur joueur;
    private Client clientMock;
    private Serveur serveurMock;

    @BeforeEach
    public void setUp() {
        joueur = new Joueur();
        clientMock = mock(Client.class);
    }

    @Test
    public void testJouerCoup() throws IOException {
        // Simule une entrée utilisateur pour la décision
        String coup = "c";
        System.setIn(new ByteArrayInputStream(coup.getBytes()));

        // Appel de la méthode jouerCoup
        String result = joueur.jouerCoup(clientMock);

        // Vérifier que le coup a bien été joué et envoyé au client
        assertEquals(coup, result);
        verify(clientMock, times(1)).envoyerCoup(coup);
    }

    @Test
    public void testDecisionCooperation() {
        // Simuler une entrée utilisateur pour la coopération
        String coup = "c";
        System.setIn(new ByteArrayInputStream(coup.getBytes()));

        // Appel de la méthode decision
        String result = joueur.decision();

        // Vérifier que le coup "cooperer" est renvoyé
        assertEquals("c", result);
    }

    @Test
    public void testDecisionTrahison() {
        // Simuler une entrée utilisateur pour la trahison
        String coup = "t";
        System.setIn(new ByteArrayInputStream(coup.getBytes()));

        // Appel de la méthode decision
        String result = joueur.decision();

        // Vérifier que le coup "trahir" est renvoyé
        assertEquals("t", result);
    }

    @Test
    public void testDecisionAbandon() {
        // Simuler une entrée utilisateur pour l'abandon
        String coup = "a";
        System.setIn(new ByteArrayInputStream(coup.getBytes()));

        // Appel de la méthode decision
        String result = joueur.decision();

        // Vérifier que le coup "abandon" est renvoyé
        assertEquals("a", result);
    }

    @Test
    public void testAbandonner_StrategieDonnantDonnant() {
        // Simuler l'entrée utilisateur pour choisir la stratégie "dd"
        String input = "dd";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Appel de la méthode abandonner
        String result = joueur.abandonner();

        // Vérifier que la stratégie "dd" est renvoyée et que l'abandon est true
        assertEquals("dd", result);
        assertTrue(joueur.isAbandon());
    }

    @Test
    public void testAbandonner_StrategieToujoursTrahir() {
        // Simuler l'entrée utilisateur pour choisir la stratégie "t"
        String input = "t";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Appel de la méthode abandonner
        String result = joueur.abandonner();

        // Vérifier que la stratégie "t" est renvoyée et que l'abandon est true
        assertEquals("t", result);
        assertTrue(joueur.isAbandon());
    }

    @Test
    public void testAbandonner_StrategieToujoursCooperer() {
        // Simuler l'entrée utilisateur pour choisir la stratégie "c"
        String input = "c";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Appel de la méthode abandonner
        String result = joueur.abandonner();

        // Vérifier que la stratégie "c" est renvoyée et que l'abandon est true
        assertEquals("c", result);
        assertTrue(joueur.isAbandon());
    }

    @Test
    public void testAbandonner_StrategieRancunier() {
        // Simuler l'entrée utilisateur pour choisir la stratégie "r"
        String input = "r";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Appel de la méthode abandonner
        String result = joueur.abandonner();

        // Vérifier que la stratégie "r" est renvoyée et que l'abandon est true
        assertEquals("r", result);
        assertTrue(joueur.isAbandon());
    }

    @Test
    public void testAbandonner_StrategiePavlov() {
        // Simuler l'entrée utilisateur pour choisir la stratégie "p"
        String input = "p";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Appel de la méthode abandonner
        String result = joueur.abandonner();

        // Vérifier que la stratégie "p" est renvoyée et que l'abandon est true
        assertEquals("p", result);
        assertTrue(joueur.isAbandon());
    }

    @Test
    public void testGetScore() {
        // Vérifier que le score initial est 0
        assertEquals(0, joueur.getScore());
    }

    @Test
    public void testSetNom() {
        // Simuler l'entrée utilisateur pour le nom
        String nom = "Alice";
        System.setIn(new ByteArrayInputStream(nom.getBytes()));

        // Appel de la méthode setNom
        joueur.setNom();

        // Vérifier que le nom a été correctement défini
        assertEquals("alice", joueur.getNom());
    }

    @Test
    public void testGetNbTours() {
        // Simuler l'entrée utilisateur pour le nombre de tours
        String nbTours = "10";
        System.setIn(new ByteArrayInputStream(nbTours.getBytes()));

        // Appel de la méthode getNbTours
        String result = joueur.getNbTours();

        // Vérifier que le nombre de tours est bien retourné
        assertEquals("10", result);
    }

    @Test
    public void testInitialValues() {
        assertEquals(0, joueur.getScore(), "Le score initial devrait être 0");
        assertNull(joueur.getNom(), "Le nom initial devrait être null");
        assertFalse(joueur.isAbandon(), "L'état d'abandon initial devrait être false");
    }

    @Test
    void testDecisionInvalidInput() {
        // Simuler une entrée invalide et ensuite une entrée valide ("c")
        String input = "x\nc";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Appel de la méthode decision
        String result = joueur.decision();

        // Vérifier que l'entrée incorrecte a été ignorée et que la décision correcte est renvoyée
        assertEquals("c", result);
    }

    @Test
    void testSetNomEmpty() {
        // Simuler une entrée vide pour le nom
        String input = "pipi";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        // Appel de la méthode setNom
        joueur.setNom();

        // Vérifier que le nom est bien vide
        assertEquals("pipi", joueur.getNom());
    }





}

