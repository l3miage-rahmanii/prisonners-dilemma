package fr.uga.l3miage.pc.responses;

import fr.uga.l3miage.pc.enums.StrategieEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartieResponseDTO {
    private Long id;
    private String nom;
    private String status;

    // Informations du jeu
    private List<String> coupsJoueur1;
    private List<String> coupsJoueur2;
    private int scoreJoueur1;
    private int scoreJoueur2;
    private StrategieEnum strategieJoueur1;
    private StrategieEnum strategieJoueur2;


    // État de la partie
    private int tourActuel;
    private boolean partieTerminee;
    private Long joueurGagnantId;
    private String messageResultat;

    // Timestamps
    private LocalDateTime dateCreation;
    private LocalDateTime dateDernierCoup;
    private LocalDateTime dateFinPartie;
}