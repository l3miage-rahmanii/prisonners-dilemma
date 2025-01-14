package fr.uga.l3miage.pc.responses;

import fr.uga.l3miage.pc.enums.StrategieEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoueurResponseDTO {
    private Long id;
    private String nom;
    private String email;

    // Statistiques
    private Integer score;
    private Integer nbVictoires;
    private Integer nbParties;
    private Integer nbPartiesAbandonnees;
    private Double scoreMoyen;
    private Double tauxCooperation;
    private Double tauxTrahison;
    private Double tauxVictoire;
    private Double tauxAbandon;

    // Stratégies préférées
    private Map<StrategieEnum, Integer> strategiesUtilisees;
}