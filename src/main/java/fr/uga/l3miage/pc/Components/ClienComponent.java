package fr.uga.l3miage.pc.Components;

import fr.uga.l3miage.pc.Entities.ClientEntity;
import fr.uga.l3miage.pc.Repositories.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClienComponent {
    private final ClientRepository clientRepository;

    public ClientEntity createClient() {
        ClientEntity clientEntity = ClientEntity
                .builder()
                .nom("Alice")
                .nbTours(5)
                .strategie("coopérer")
                .build();
        return clientRepository.save(clientEntity);
    }
}
