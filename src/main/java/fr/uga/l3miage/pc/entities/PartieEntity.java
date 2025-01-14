package fr.uga.l3miage.pc.entities;


import fr.uga.l3miage.pc.enums.StrategieEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "partie")
public class PartieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String status;
    private Integer nbTours;

    @ManyToOne
    @JoinColumn(name = "gagnant_id")
    private JoueurEntity gagnant;

    @OneToOne
    @JoinColumn(name = "serveur_id", nullable = false)
    private ServeurEntity serveur;

    @ManyToMany
    @JoinTable(
            name = "partie_joueurs",
            joinColumns = @JoinColumn(name = "partie_id"),
            inverseJoinColumns = @JoinColumn(name = "joueur_id")
    )
    private List<JoueurEntity> joueurs;

    // Ajout des champs pour gérer les coups
    @ElementCollection
    @CollectionTable(name = "coups_joueurs",
            joinColumns = @JoinColumn(name = "partie_id"))
    private List<String> coupsJoueur1 = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "coups_joueurs",
            joinColumns = @JoinColumn(name = "partie_id"))
    private List<String> coupsJoueur2 = new ArrayList<>();

    private int scoreJoueur1;
    private int scoreJoueur2;
    private StrategieEnum strategieJoueur1;
    private StrategieEnum strategieJoueur2;
}
