package fr.uga.l3miage.pc.repositories;


import fr.uga.l3miage.pc.entities.ServeurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServeurRepository extends JpaRepository<ServeurEntity, Long> {
    Optional<ServeurEntity> findById(Long id);
}