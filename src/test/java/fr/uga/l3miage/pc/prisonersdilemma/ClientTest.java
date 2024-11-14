package fr.uga.l3miage.pc.prisonersdilemma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class ClientTest {
    private Client client;
    private Joueur joueurMock;
    private Socket socketMock;
    private PrintWriter outMock;
    private BufferedReader inMock;

    @BeforeEach
    public void setUp() {
        joueurMock = mock(Joueur.class);
        client = new Client(joueurMock);
        outMock = mock(PrintWriter.class);
        inMock = mock(BufferedReader.class);
        socketMock = mock(Socket.class);

    }


    @Test
    public void testTraiterMessageInvalide() throws IOException {
        when(inMock.readLine()).thenReturn("Message inconnu");
        client.receptionMessageDuClient("Message inconnu");
        // Vérifier qu'aucune autre méthode n'a été appelée
        verify(joueurMock, never()).setNom();
        verify(joueurMock, never()).getNbTours();
    }

    @Test
    public void testAskName() throws IOException {
        when(joueurMock.getNom()).thenReturn("Alice");
        client.setOut(outMock);
        client.askName();
        verify(outMock).println("Alice");
    }

    @Test
    public void testAskTours() throws IOException {
        when(joueurMock.getNbTours()).thenReturn("10");
        client.setOut(outMock);
        client.askTours();
        verify(outMock).println("10");
    }

    @Test
    public void testAskStrategie() throws IOException {
        when(joueurMock.abandonner()).thenReturn("toujours_cooperer");
        client.setOut(outMock);
        client.askStategie();
        verify(outMock).println("toujours_cooperer");
    }

    @Test
    public void testRecevoirCoup() throws IOException {
        when(joueurMock.decision()).thenReturn("c");
        String coup = client.recevoirCoup();
        assertEquals("c", coup);
    }

    @Test
    public void testEnvoyerCoup() throws IOException {
        client.setOut(outMock);
        String coup = "c";
        client.envoyerCoup(coup);
        verify(outMock, times(1)).println(coup);
    }

    @Test
    public void testSeDeconnecter() throws IOException {
        client.setIn(inMock);
        client.setOut(outMock);
        client.setSocket(socketMock);
        client.seDeconnecter();
        verify(inMock, times(1)).close();
        verify(outMock, times(1)).close();
        verify(socketMock, times(1)).close();
    }


    @Test
    public void testReceptionMessageDuClientWithBienvenueMessage() throws IOException {
        client.setIn(inMock);
        client.setOut(outMock);
        when(joueurMock.getNom()).thenReturn("Player1");
        client.receptionMessageDuClient("Bienvenue ! Veuillez choisir un nom :");
        verify(outMock, times(1)).println("Player1");
    }

    @Test
    public void testReceptionMessageDuClientWithNbToursMessage() throws IOException {
        client.setIn(inMock);
        client.setOut(outMock);
        when(joueurMock.getNbTours()).thenReturn("10");
        client.receptionMessageDuClient("Veuillez choisir le nombre de tours :");
        verify(outMock, times(1)).println("10");
    }

    @Test
    public void testReceptionMessageDuClientWithNullMessage() throws IOException {
        client.receptionMessageDuClient(null);
    }

}

