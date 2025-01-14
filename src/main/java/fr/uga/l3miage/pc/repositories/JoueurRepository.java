package fr.uga.l3miage.pc.repositories;

import fr.uga.l3miage.pc.entities.JoueurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface JoueurRepository extends JpaRepository<JoueurEntity, Long> {

}