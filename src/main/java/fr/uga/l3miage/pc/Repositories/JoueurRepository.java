package fr.uga.l3miage.pc.Repositories;


import fr.uga.l3miage.pc.Entities.JoueurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JoueurRepository extends JpaRepository<JoueurEntity, Long> {
    JoueurEntity findByNom(String nom); // Exemple : Trouver un joueur par son nom
}
