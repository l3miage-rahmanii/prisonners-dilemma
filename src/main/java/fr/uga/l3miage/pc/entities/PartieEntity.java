package fr.uga.l3miage.pc.entities;


import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne
    @JoinColumn(name = "serveur_id", nullable = false)
    private ServeurEntity serveur;

    @ManyToMany
    @JoinTable(
            name = "partie_joueurs",
            joinColumns = @JoinColumn(name = "partie_id"),
            inverseJoinColumns = @JoinColumn(name = "joueur_id")
    )
    private List<JoueurEntity> joueurs;
}