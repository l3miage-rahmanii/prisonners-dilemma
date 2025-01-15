package fr.uga.l3miage.pc.responses;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartieResponseDTO {
    private String id;
    private int idJoueur1;
    private int idJoueur2;
    private String status;
    private int scoreJoueur1;
    private int scoreJoueur2;
    private int tourActuel;
    private boolean partieTerminee;
    private int joueurGagnantId;
    private String messageResultat;

}