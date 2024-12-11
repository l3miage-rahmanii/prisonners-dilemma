package fr.uga.l3miage.pc.Repositories;


import fr.uga.l3miage.pc.Entities.ServeurEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServeurRepository extends JpaRepository<ServeurEntity, Long> {
    Optional<ServeurEntity> findById(Long id);
}