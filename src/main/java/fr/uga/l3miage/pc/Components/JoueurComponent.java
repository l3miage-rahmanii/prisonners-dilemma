package fr.uga.l3miage.pc.Components;

import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class JoueurComponent {

    private static final Logger logger = Logger.getLogger(JoueurComponent.class.getName());
    private String nom;
    private boolean abandon;
    private int score;

    public JoueurComponent() {
        this.nom = null;
        this.abandon = false;
        this.score = 0;
    }

    /**
     * M�thode pour d�finir le nom du joueur.
     */
    public void setNom() {
        Scanner scanner = new Scanner(System.in);
        logger.log(Level.INFO, "Veuillez entrer votre nom : ");
        this.nom = scanner.nextLine();
    }

    /**
     * M�thode pour obtenir le nom du joueur.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Prendre une d�cision : coop�rer, trahir ou abandonner.
     *
     * @return La d�cision du joueur ("c", "t", ou "a").
     */
    public String prendreDecision() {
        Scanner scanner = new Scanner(System.in);
        String decision;
        do {
            logger.log(Level.INFO, "{0}, voulez-vous trahir (t), coop�rer (c), ou abandonner (a) ?", this.nom);
            decision = scanner.nextLine().toLowerCase();
        } while (!decision.equals("c") && !decision.equals("t") && !decision.equals("a"));
        return decision;
    }

    /**
     * G�rer l'abandon du joueur.
     *
     * @return La strat�gie choisie apr�s l'abandon.
     */
    public String abandonner() {
        this.abandon = true;
        Scanner scanner = new Scanner(System.in);
        String strategie;
        do {
            logger.log(Level.INFO, "Vous avez abandonn�. Choisissez une strat�gie : Donnant-Donnant (dd), Toujours Trahir (t), Toujours Coop�rer (c), Rancunier (r), ou Pavlov (p).");
            strategie = scanner.nextLine().toLowerCase();
        } while (!strategie.equals("dd") && !strategie.equals("t") && !strategie.equals("c") && !strategie.equals("r") && !strategie.equals("p"));
        return strategie;
    }

    /**
     * Obtenir le score actuel du joueur.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Mettre � jour le score du joueur.
     *
     * @param points Les points � ajouter au score.
     */
    public void ajouterPoints(int points) {
        this.score += points;
    }

    /**
     * V�rifie si le joueur a abandonn�.
     */
    public boolean isAbandon() {
        return this.abandon;
    }
}
