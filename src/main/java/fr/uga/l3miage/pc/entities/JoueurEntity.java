package fr.uga.l3miage.pc.entities;



import fr.uga.l3miage.pc.enums.StrategieEnum;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "joueur")
public class JoueurEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Integer score;
    private Integer nbVictoires;
    private Integer nbParties;
    private Double scoreMoyen;
    private Double tauxCooperation;
    private Double tauxVictoire;
    private String dernierCoup;

    @ElementCollection
    @CollectionTable(name = "strategies_utilisees",
            joinColumns = @JoinColumn(name = "joueur_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @Column(name = "nombre_utilisations")
    private Map<StrategieEnum, Integer> strategiesUtilisees;

    @ManyToMany
    @JoinTable(
            name = "joueur_serveurs",
            joinColumns = @JoinColumn(name = "joueur_id"),
            inverseJoinColumns = @JoinColumn(name = "serveur_id")
    )
    private List<ServeurEntity> serveurs;
}
