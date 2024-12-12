package fr.uga.l3miage.pc.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ServeurResponseDTO {
    private Long id;
    private String status;
    private String adresse;
}