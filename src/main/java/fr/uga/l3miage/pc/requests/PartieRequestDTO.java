package fr.uga.l3miage.pc.requests;

import fr.uga.l3miage.pc.enums.StrategieEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartieRequestDTO {
    @NotBlank(message = "Le nom de la partie est obligatoire")
    private String nom;

    @NotNull(message = "L'ID du serveur est obligatoire")
    private Long serveurId;

    @NotEmpty(message = "Au moins un joueur est requis")
    private List<Long> joueurIds;

    private StrategieEnum strategieJoueur1;
    private StrategieEnum strategieJoueur2;
    private Integer nbTours;
}