package fr.uga.l3miage.pc.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartieRequestDTO {
    private Long serveurId;
    private List<Long> joueursIds;
    private String nom;
}