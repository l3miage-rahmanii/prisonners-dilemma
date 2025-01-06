package fr.uga.l3miage.pc.repositories;

import fr.uga.l3miage.pc.entities.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartieRepository extends JpaRepository<PartieEntity, Long> {
    Optional<PartieEntity> findById(Long id);
}
