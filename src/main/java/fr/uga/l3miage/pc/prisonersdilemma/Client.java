package fr.uga.l3miage.pc.prisonersdilemma;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    Joueur joueur;

    //Chaque Client repr?sente un joueur
    public Client (Joueur joueur){
        this.joueur = joueur;
    }

    //Connexion au serveur
    public void seConnecter (String adresse,int port) throws IOException{
        socket = new Socket(adresse,port);
        out = new PrintWriter(socket.getOutputStream(),true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        System.out.println("Connecte au serveur sur " + adresse + ":" +port);
    }

    public void envoyerCoup(String coup) throws IOException{
        out.println(coup);
    }

    public String recevoirCoup () throws IOException{
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

    public void seDeconnecter() throws IOException{
        in.close();
        out.close();
        socket.close();
        System.out.println("Deconnecte du serveur.");
    }

    //Demmande de la stratégie voulue par le joueur lors de son abandon
    public void askStategie() throws IOException{
       out.println(joueur.abandonner());
    }

    public void receptionMessageDuClient(String messageRecu) throws IOException {
        if (messageRecu != null) {
            System.out.println(messageRecu);
            switch (messageRecu) {
                case "Bienvenue ! Veuillez choisir un nom :":
                    this.askName();
                    break;
                case "Veuillez choisir le nombre de tours :":
                    this.askTours();
                    break;
                case "C'est à votre tour de jouer.":
                    this.envoyerCoup(this.recevoirCoup());

            }
        }
    }

    public static void main(String[] args){
            Joueur joueur = new Joueur();  // Créer un joueur (le client)
            Client client = new Client(joueur);  // Créer un client
            try {
                client.seConnecter("localhost", 8080);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.socket.getInputStream()));
                    while(true) {
                        String message = in.readLine();  // Lecture du message envoyé par le serveur
                        client.receptionMessageDuClient(message);
                    }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}