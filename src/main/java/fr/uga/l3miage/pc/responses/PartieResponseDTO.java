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
    private int scoreJoueur1;
    private int scoreJoueur2;
    private int tourActuel;
    private boolean partieTerminee;
    private int joueurGagnantId;
    private String messageResultat;

}