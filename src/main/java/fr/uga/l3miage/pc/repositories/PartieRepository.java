package fr.uga.l3miage.pc.repositories;

import fr.uga.l3miage.pc.entities.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartieRepository extends JpaRepository<PartieEntity, Long> {
    List<PartieEntity> findByServeurId(Long serveurId);
    List<PartieEntity> findByJoueursId(Long joueurId);
    Optional<PartieEntity> findByIdAndJoueursId(Long partieId, Long joueurId);
}