package fr.uga.l3miage.pc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PartieResponseDTO {
    private Long id;
    private String nom;
    private String status;
}