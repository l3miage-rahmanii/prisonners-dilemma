package fr.uga.l3miage.pc.Entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ServeurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomJoueur1;
    private String nomJoueur2;
    private int nbTours;
    private int scoreTotalClient1;
    private int scoreTotalClient2;
}
