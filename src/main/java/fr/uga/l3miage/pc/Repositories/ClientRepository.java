package fr.uga.l3miage.pc.Repositories;

import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.prisonersdilemma.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findById(Long aLong);
}
