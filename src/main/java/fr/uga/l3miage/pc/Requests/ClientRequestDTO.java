package fr.uga.l3miage.pc.Requests;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientRequestDTO {
    private String nom;
    private String adresse;
}
