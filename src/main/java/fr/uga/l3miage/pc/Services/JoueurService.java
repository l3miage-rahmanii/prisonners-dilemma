package fr.uga.l3miage.pc.Services;


import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Repositories.JoueurRepository;
import fr.uga.l3miage.pc.Components.JoueurComponent;
import org.springframework.stereotype.Service;


@Service
public class JoueurService {

    private final JoueurRepository joueurRepository;
    private final JoueurComponent joueurComponent;

    public JoueurService(JoueurRepository joueurRepository, JoueurComponent joueurComponent) {
        this.joueurRepository = joueurRepository;
        this.joueurComponent = joueurComponent;
    }

    /**
     * Enregistrer le nom d'un joueur (d�fini par le composant JoueurComponent) dans la base de donn�es.
     *
     * @param joueurId L'ID du joueur.
     */
    public void enregistrerNomDuJoueur(Long joueurId) {
        JoueurEntity joueur = joueurRepository.findById(joueurId)
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouv�."));

        joueurComponent.setNom(); // D�finir le nom avec le composant
        joueur.setNom(joueurComponent.getNom()); // R�cup�rer et assigner le nom
        joueurRepository.save(joueur); // Enregistrer dans la base de donn�es
    }

    /**
     * Jouer un tour : G�rer la d�cision du joueur (coop�rer, trahir, abandonner).
     *
     * @param joueurId L'ID du joueur.
     */
    public void jouerTour(Long joueurId) {
        JoueurEntity joueur = joueurRepository.findById(joueurId)
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouv�."));

        String decision = joueurComponent.prendreDecision(); // Prendre une d�cision via le composant

        switch (decision) {
            case "c": // Coop�rer
                joueur.setScore(joueur.getScore() + 1); // Ajoute des points en cas de coop�ration
                break;
            case "t": // Trahir
                joueur.setScore(joueur.getScore() + 3); // Ajoute des points pour trahison
                break;
            case "a": // Abandonner
                joueur.setAbandon(true);
                String strategie = joueurComponent.abandonner(); // Choisir une strat�gie post-abandon
                joueur.setStrategiePostAbandon(strategie);
                break;
            default:
                throw new IllegalArgumentException("D�cision invalide : " + decision);
        }

        joueurRepository.save(joueur); // Mettre � jour la base de donn�es
    }

    /**
     * Obtenir les informations d'un joueur.
     *
     * @param joueurId L'ID du joueur.
     * @return L'objet `JoueurEntity` correspondant.
     */
    public JoueurEntity obtenirJoueur(Long joueurId) {
        return joueurRepository.findById(joueurId)
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouv�."));
    }

    /**
     * Ajouter des points au score du joueur.
     *
     * @param joueurId L'ID du joueur.
     * @param points   Les points � ajouter.
     */
    public void ajouterPoints(Long joueurId, int points) {
        JoueurEntity joueur = joueurRepository.findById(joueurId)
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouv�."));

        joueur.setScore(joueur.getScore() + points);
        joueurRepository.save(joueur); // Sauvegarder les modifications
    }

    /**
     * R�initialiser l'�tat d'un joueur (nom, score, abandon, etc.).
     *
     * @param joueurId L'ID du joueur.
     */
    public void reinitialiserJoueur(Long joueurId) {
        JoueurEntity joueur = joueurRepository.findById(joueurId)
                .orElseThrow(() -> new IllegalArgumentException("Joueur non trouv�."));

        joueur.setNom(null);
        joueur.setScore(0);
        joueur.setAbandon(false);
        joueur.setStrategiePostAbandon(null);
        joueurRepository.save(joueur);
    }
}
