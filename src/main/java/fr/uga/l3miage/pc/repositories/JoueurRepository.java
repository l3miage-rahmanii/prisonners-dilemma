package fr.uga.l3miage.pc.repositories;

import fr.uga.l3miage.pc.entities.JoueurEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface JoueurRepository extends JpaRepository<JoueurEntity, Long> {

}