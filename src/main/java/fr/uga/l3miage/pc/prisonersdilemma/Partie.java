package fr.uga.l3miage.pc.prisonersdilemma;

import fr.uga.l3miage.pc.stratégies.Strategie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Partie {
    private Client client1;
    private Client client2;
    private int nombreTours;
    private Serveur serveur;
    private boolean abandon;
    private static Partie instance;

    private Partie(Client client1, Client client2, int nombreTours) {
        serveur = Serveur.getInstance();
        this.client1 = client1;
        this.client2 = client2;
        this.nombreTours = nombreTours;
        this.abandon = false;
    }

    public static Partie getInstance(Client client1, Client client2, int nombreTours) {
        if (instance == null) {
            instance = new Partie(client1, client2, nombreTours);
        }
        return instance;
    }


    public void commencer() throws IOException {
        for (int i = 0; i < nombreTours; i++) {
            System.out.println("Tour " + i);
            if(abandon){
                nombreTours -= i;
                break;
            }
            serveur.askCoup(client1);
            String coupJoueur1 = serveur.getCoup(client1);
            serveur.askCoup(client2);
            String coupJoueur2 = serveur.getCoup(client2);
            serveur.calculScore(coupJoueur1,coupJoueur2,i);
            serveur.envoyerScores();

        }

        fin();
    }

    public void partieSuivantAbandon(Client client, Strategie strategie) throws IOException {
            this.abandon = true;
            for (int i = 1; i <= nombreTours; i++) {
                serveur.askCoup(client);
                serveur.calculScoreCasAbandon(strategie.prochainCoup(), client);
                serveur.envoyerScoresCasAbandon(client);
            }
            finAbandon(client);
    }

    public void fin() throws IOException {
        serveur.vainceur();
        serveur.stop();
    }

    public void finAbandon(Client client) throws IOException {
        serveur.vainceurAbandon(client);
        serveur.stop();
    }
}


