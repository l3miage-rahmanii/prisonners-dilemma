package fr.uga.l3miage.pc.Responses;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponseDTO {
    private Long id;
    private String nom;
    private String adresse;
}