package fr.uga.l3miage.pc.Repositories;

import fr.uga.l3miage.pc.Entities.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartieRepository extends JpaRepository<PartieEntity, Long> {
}

