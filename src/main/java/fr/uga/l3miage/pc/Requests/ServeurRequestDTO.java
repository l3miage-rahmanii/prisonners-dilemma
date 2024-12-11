package fr.uga.l3miage.pc.Requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServeurRequestDTO {
    private String status;
    private String adresse;
}
