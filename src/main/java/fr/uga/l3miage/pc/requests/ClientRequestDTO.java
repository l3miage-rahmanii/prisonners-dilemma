package fr.uga.l3miage.pc.requests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {
    private String nom;
    private String adresse;
}
