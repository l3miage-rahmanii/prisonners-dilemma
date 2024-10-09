package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    private static Serveur instance;
    private ServerSocket serverSocket;
    private BufferedReader inClient1;
    private BufferedReader inClient2;
    private PrintWriter outClient1;
    private PrintWriter outClient2;
    private String coup;
    private int scoreTotalClient1;
    private int scoreTotalClient2;

    //Constructeur priv� pour le patron Singleton
    private Serveur() {
        this.scoreTotalClient1 = 0;
        this.scoreTotalClient2 = 0;
    }

    //M�thode statique pour obtenir l'instance unique du serveur
    public static Serveur getInstance() {
        if (instance == null) {
            instance = new Serveur();
        }
        return instance;
    }

    //M�thode pour d�marrer le serveur
    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        System.out.println("Serveur d�mar� sur le port " + port);

        //Le serveur doit s'assusrer que deux joueurs ( clients ) soient connect�s
        System.out.println("En attente du premier joueur...");
        Socket client1Socket = serverSocket.accept();
        System.out.println("Joueur 1 connect�");

        System.out.println("En attente du second joueur...");
        Socket client2Socket = serverSocket.accept();
        System.out.println("Joueur 2 connect�");

        outClient1 = new PrintWriter(client1Socket.getOutputStream(), true);
        outClient2 = new PrintWriter(client2Socket.getOutputStream(), true);
        inClient1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
        inClient2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));

        //Cr�er et lancer la partie
        new Partie().commencer();
    }

    public void calculScore() throws IOException {
        int scoreClient1 = 0, scoreClient2 = 0;
        String coupClient1 = inClient1.readLine();
        String coupClient2 = inClient2.readLine();

        if (coupClient1.equals("c") && coupClient2.equals("c")) {
            scoreClient1 = 3;
            scoreClient2 = scoreClient1;
        }
        else if (coupClient1.equals("c") && coupClient2.equals("t")) {
            scoreClient1 = 5;
            scoreClient2 = 0;
        }
        else if (coupClient1.equals("t") && coupClient2.equals("c")) {
            scoreClient1 = 0;
            scoreClient2 = 5;
        }
        else if (coupClient1.equals("t") && coupClient2.equals("t")) {
            scoreClient1 = 1;
            scoreClient2 = 1;
        }

        scoreTotalClient1 += scoreClient1;
        scoreTotalClient2 += scoreClient2;
    }

    public void envoyerScores() throws IOException {
        outClient1.println("Voici les scores : Vous = "+ scoreTotalClient1 + ", Votre adversaire = " +scoreTotalClient2);
        outClient2.println("Voici les scores : Vous = "+ scoreTotalClient2 + ", Votre adversaire = " +scoreTotalClient1);
        if (scoreTotalClient1 > scoreTotalClient2) {
            outClient1.println("Le gagnant de ce tour est : " + scoreTotalClient1);
            outClient2.println("Le gagnant de ce tour est : " + scoreTotalClient1);
        }
        else if (scoreTotalClient2 > scoreTotalClient1) {
            outClient1.println("Le gagnant de ce tour est : " + scoreTotalClient2);
            outClient2.println("Le gagnant de ce tour est : " + scoreTotalClient2);
        }
        else if (scoreTotalClient2 == scoreTotalClient2) {
            outClient1.println("�galit� !");
            outClient2.println("�galit� !");
        }
    }

    public int demanderNbTours() throws IOException {
     int nbTours = 0;
     outClient1.println("Combien de tours voulez-vous jouer ?");
     String input = inClient1.readLine();
     try {
     nbTours = Integer.parseInt(input);
     if (nbTours <= 0) {
         outClient1.println("Veuillez entrer un nombre positif.");
         demanderNbTours();
     }
     else {
         outClient2.println("Le nombre de tours choisi est : "+ nbTours);
     }
     }
     catch (NumberFormatException e) {
         outClient1.println("Entr�e invalide. Veuillez entrer un nombre valide.");
         demanderNbTours();
     }
     return nbTours;
    }

    //M�thode pour arr�ter le serveur
    public void stop() throws IOException {
        if (serverSocket != null && !serverSocket.isClosed()) {
            serverSocket.close();
        }
    }
}