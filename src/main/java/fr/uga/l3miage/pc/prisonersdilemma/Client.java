package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {
    /*
    private static final Logger logger = Logger.getLogger(Client.class.getName());
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private Joueur joueur;

    //Chaque Client représente un joueur
    public Client(Joueur joueur) {
        this.joueur = joueur;
    }

    //Connexion au serveur
    public void seConnecter(String adresse, int port) throws IOException {
        socket = new Socket(adresse, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        logger.log(Level.INFO, "Connecté au serveur sur {0}:{1}", new Object[]{adresse, port});
    }

    public void envoyerCoup(String coup) throws IOException {
        out.println(coup);
    }

    public String recevoirCoup() throws IOException {
        return joueur.decision();
    }

    public void askName() {
        joueur.setNom();
        out.println(joueur.getNom());
    }

    public void askTours() {
        String NbTours = joueur.getNbTours();
        out.println(NbTours);
    }

    public void seDeconnecter() throws IOException {
        in.close();
        out.close();
        socket.close();
        logger.log(Level.INFO, "Déconnecté du serveur.");
    }

    // Demande de la stratégie voulue par le joueur lors de son abandon
    public void askStategie() throws IOException {
        out.println(joueur.abandonner());
    }

    public void receptionMessageDuClient(String messageRecu) throws IOException {
        if (messageRecu != null) {
            logger.log(Level.INFO, messageRecu);
            switch (messageRecu) {
                case "Bienvenue ! Veuillez choisir un nom :":
                    this.askName();
                    break;
                case "Veuillez choisir le nombre de tours :":
                    this.askTours();
                    break;
                case "C'est à votre tour de jouer.":
                    this.envoyerCoup(this.recevoirCoup());
                    break;
                default:
                    logger.log(Level.WARNING, "Message inconnu reçu: {0}", messageRecu);
            }
        }
    }

    public static void main(String[] args) {
        Joueur joueur = new Joueur();  // Créer un joueur (le client)
        Client client = new Client(joueur); // Créer un client
        try {
            client.seConnecter("localhost", 8080);
            BufferedReader in = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
            String message = in.readLine();  // Lecture du message envoyé par le serveur
            while (message != null) {
                client.receptionMessageDuClient(message);
                message = in.readLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Erreur de connexion. Veuillez contacter le support.", e);
        }
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

     */
}
