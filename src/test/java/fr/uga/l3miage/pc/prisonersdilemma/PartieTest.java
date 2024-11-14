package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.strategies.Strategie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.*;

public class PartieTest {

    private Partie partie;
    private Client clientMock1;
    private Client clientMock2;
    private Serveur serveurMock;
    private Strategie strategieMock;
    private PrintWriter outMock1;
    private BufferedReader inMock1;
    private PrintWriter outMock2;
    private BufferedReader inMock2;

    @BeforeEach
    public void setUp() {
        clientMock1 = mock(Client.class);
        clientMock2 = mock(Client.class);
        serveurMock = mock(Serveur.class);
        strategieMock = mock(Strategie.class);
        outMock1 = mock(PrintWriter.class);
        inMock1 = mock(BufferedReader.class);
        outMock2 = mock(PrintWriter.class);
        inMock2 = mock(BufferedReader.class);
        partie = Partie.getInstance(clientMock1, clientMock2, 3); // Crée une partie avec 3 tours
    }

    @Test
    public void testSingletonInstance() {
        Partie autreInstance = Partie.getInstance(clientMock1, clientMock2, 3);
        assertSame(partie, autreInstance, "La classe Partie devrait suivre le patron Singleton");
    }



}
