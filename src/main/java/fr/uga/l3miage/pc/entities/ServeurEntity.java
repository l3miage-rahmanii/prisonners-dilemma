package fr.uga.l3miage.pc.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "serveur")
public class ServeurEntity {
    @Id
    private Long id;
    private String status;
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private static ServeurEntity instance;

    public static ServeurEntity getInstance() {
        if (instance == null) {
            instance = new ServeurEntity();
        }
        return instance;
    }
}
