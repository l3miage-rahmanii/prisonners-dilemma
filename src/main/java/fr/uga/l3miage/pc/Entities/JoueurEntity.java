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
@Getter
@Setter
public class JoueurEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom; // Nom du joueur
    private boolean abandon; // Statut d'abandon
    private String strategie; // Stratégie choisie en cas d'abandon (ex. "dd", "t", "c", etc.)
    private int score; // Score total du joueur
    private String strategiePostAbandon;
}

