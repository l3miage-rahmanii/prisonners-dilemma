package fr.uga.l3miage.pc.prisonersdilemma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServeurTest {

    private Serveur serveur;
    private Client client1;
    private Client client2;
    private PrintWriter outMock1;
    private BufferedReader inMock1;
    private PrintWriter outMock2;
    private BufferedReader inMock2;

    @BeforeEach
    public void setup() {
        serveur = Serveur.getInstance();
        client1 = mock(Client.class);
        client2 = mock(Client.class);
        serveur.setClient1(client1);
        serveur.setClient2(client2);
        serveur.setScoreTotalClient2(0);
        serveur.setScoreTotalClient1(0);
        outMock1 = mock(PrintWriter.class);
        inMock1 = mock(BufferedReader.class);
        outMock2 = mock(PrintWriter.class);
        inMock2 = mock(BufferedReader.class);
    }

    // Test 1: Singleton pattern
    @Test
    public void testServeurInstanceUnique() {
        Serveur instance1 = Serveur.getInstance();
        Serveur instance2 = Serveur.getInstance();
        assertSame(instance1, instance2, "L'instance de Serveur doit être unique.");
    }


    // Test 3: Score calculation - mutual cooperation
    @Test

    public void testCalculScoreCooperationMutuelle() throws IOException {
        serveur = Serveur.getInstance();

        serveur.calculScore("c", "c", 0);

        assertEquals(3, serveur.getScoreTotalClient1(), "Client 1 devrait avoir 3 points en coopération mutuelle.");

        assertEquals(3, serveur.getScoreTotalClient2(), "Client 2 devrait avoir 3 points en coopération mutuelle.");
    }
    // Test 4: Score calculation - betrayal scenario
    @Test
    public void testCalculScoreTrahisonUnique() throws IOException {
        serveur.setHistoriqueClient(1);
        serveur.calculScore("c", "t", 0);
        assertEquals(0, serveur.getScoreTotalClient1(), "Client 1 devrait avoir 0 points en cas de trahison unique.");
        assertEquals(5, serveur.getScoreTotalClient2(), "Client 2 devrait avoir 5 points en cas de trahison unique.");
    }

    // Test 5: Score calculation - mutual betrayal
    @Test
    public void testCalculScoreTrahisonMutuelle() throws IOException {
        serveur.setHistoriqueClient(1);
        serveur.calculScore("t", "t", 0);
        assertEquals(1, serveur.getScoreTotalClient1(), "Client 1 devrait avoir 1 point en trahison mutuelle.");
        assertEquals(1, serveur.getScoreTotalClient2(), "Client 2 devrait avoir 1 point en trahison mutuelle.");
    }

    @Test
    public void testAskCoupClient1() throws IOException {
        serveur.setOutClient1(outMock1);
        serveur.askCoup(client1);
        verify(outMock1, times(1)).println("C'est a votre tour de jouer.");
    }

    @Test
    public void testAskCoupClient2() throws IOException {
        serveur.setOutClient2(outMock2);
        serveur.askCoup(client2);
        verify(outMock2, times(1)).println("C'est a votre tour de jouer.");
    }

    @Test
    public void testGetCoupClient1() throws IOException {
        serveur.setInClient1(inMock1);
        when(inMock1.readLine()).thenReturn("c");
        String coup = serveur.getCoup(client1);
        assertEquals("c", coup);
    }

    @Test
    public void testGetCoupClient2() throws IOException {
        serveur.setInClient2(inMock2);
        when(inMock2.readLine()).thenReturn("t");
        String coup = serveur.getCoup(client2);
        assertEquals("t", coup);
    }

    @Test
    public void testEnvoyerScores() throws IOException {
        serveur.setOutClient1(outMock1);
        serveur.setOutClient2(outMock2);
        serveur.setScoreTotalClient1(5);
        serveur.setScoreTotalClient2(3);
        serveur.envoyerScores();
        verify(outMock1).println("Voici les scores : Vous = 5, Votre adversaire = 3");
        verify(outMock2).println("Voici les scores : Vous = 3, Votre adversaire = 5");
    }

    @Test
    public void testCalculScoreCasAbandon() throws IOException {
        serveur.setInClient1(inMock1);
        when(inMock1.readLine()).thenReturn("t");
        serveur.setHistoriqueClient(1);
        serveur.calculScoreCasAbandon("c", client1);
        assertEquals(0, serveur.getScoreTotalClient1());
        assertEquals(5, serveur.getScoreTotalClient2());
    }

}
