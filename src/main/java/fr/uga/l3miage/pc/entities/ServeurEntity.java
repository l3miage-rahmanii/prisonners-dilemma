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
@Table(name = "serveur")
public class ServeurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String description;
    private String status;

    @OneToOne(mappedBy = "serveur")
    private PartieEntity partie;

    @ManyToMany(mappedBy = "serveurs")
    private List<JoueurEntity> joueurs;
}

