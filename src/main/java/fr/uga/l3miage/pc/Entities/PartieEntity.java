package fr.uga.l3miage.pc.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PartieEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private JoueurEntity client1;

    @ManyToOne
    private JoueurEntity client2;

    private int nombreTours; // Nombre total de tours

    private boolean abandon; // Indique si un joueur a abandonn�

    private String strategieAbandon; // Strat�gie utilis�e en cas d'abandon

    private String resultat; // R�sultat ou gagnant de la partie
}

