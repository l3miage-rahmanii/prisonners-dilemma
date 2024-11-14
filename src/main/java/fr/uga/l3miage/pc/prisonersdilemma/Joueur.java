package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Joueur {
    private static final Logger logger = Logger.getLogger(Joueur.class.getName());
    private String nom;
    private boolean abandon;
    private int score;

    public Joueur() {
        this.nom = null;
        this.abandon = false;
        this.score = 0;
    }

    public String jouerCoup(Client client) throws IOException {
        String coup = decision();
        client.envoyerCoup(coup);
        return coup;
    }

    public String decision() {
        Scanner scanner = new Scanner(System.in);
        String decision;
        do {
            logger.log(Level.INFO, "{0}, Voulez vous trahir (t = trahir) ou cooperer (c = cooperer) ou abandonner (a = abandon) ?", nom);
            decision = scanner.nextLine().toLowerCase();
        } while (!decision.equals("c") && !decision.equals("t") && !decision.equals("a"));
        return decision;
    }

    public String abandonner() {
        this.abandon = true;
        Scanner scanner = new Scanner(System.in);
        String stratVoulue;
        do {
            logger.log(Level.INFO, "{0}, Vous avez décidé d'abandonner. Vous devez maintenant choisir une de ces stratégies : 1- Donnant-Donnant (dd) 2- Toujours Trahir (t) 3- Toujours Coopérer (c) 4- Rancunier (r) 5- Pavlov (p)", nom);
            stratVoulue = scanner.nextLine().toLowerCase();
        } while (!stratVoulue.equals("dd") && !stratVoulue.equals("t") && !stratVoulue.equals("c") && !stratVoulue.equals("r") && !stratVoulue.equals("p"));
        return stratVoulue;
    }

    public int getScore() {
        return score;
    }

    public String getNom() {
        return nom;
    }

    public void setNom() {
        Scanner scanner = new Scanner(System.in);
        logger.log(Level.INFO, "Nom = ");
        this.nom = scanner.nextLine().toLowerCase();
    }

    public String getNbTours() {
        Scanner scanner = new Scanner(System.in);
        logger.log(Level.INFO, "Nb Tours = ");
        return scanner.nextLine();
    }

    public boolean isAbandon() {
        return abandon;
    }
}
