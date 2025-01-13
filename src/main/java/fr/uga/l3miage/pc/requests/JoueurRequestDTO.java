package fr.uga.l3miage.pc.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoueurRequestDTO {
    private String nom;
    private String email;
}