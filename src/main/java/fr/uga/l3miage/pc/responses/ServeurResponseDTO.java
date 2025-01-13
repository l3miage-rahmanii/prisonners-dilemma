package fr.uga.l3miage.pc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServeurResponseDTO {
    private Long id;
    private String nom;
    private String description;
    private PartieResponseDTO partie;
    private List<JoueurResponseDTO> joueurs;
}