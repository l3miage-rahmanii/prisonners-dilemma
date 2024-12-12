package fr.uga.l3miage.pc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JoueurResponseDTO {
    private Long id;
    private String nom;
    private int score;
    private boolean abandon;
}

