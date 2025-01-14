package fr.uga.l3miage.pc.repositories;

import fr.uga.l3miage.pc.entities.PartieEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartieRepository extends JpaRepository<PartieEntity, Long> {
    List<PartieEntity> findByServeurId(Long serveurId);
    List<PartieEntity> findByJoueursId(Long joueurId);
    Optional<PartieEntity> findByIdAndJoueursId(Long partieId, Long joueurId);
    @Query("SELECT p FROM PartieEntity p WHERE p.status != :status")
    List<PartieEntity> findByStatusNot(@Param("status") String status);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PartieEntity p WHERE p.status = :status")
    boolean existsByStatus(@Param("status") String status);
}