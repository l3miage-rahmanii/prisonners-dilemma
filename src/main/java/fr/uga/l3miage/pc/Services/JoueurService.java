package fr.uga.l3miage.pc.Services;

import fr.uga.l3miage.pc.Repositories.JoueurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JoueurService {
/*
    private final JoueurRepository joueurRepository;

    public JoueurResponseDTO createJoueur(JoueurCreationRequest request) {
        JoueurEntity joueurEntity = JoueurEntity.builder()
                .nom(request.getNom())
                .abandon(false) // Par défaut, le joueur n'abandonne pas
                .score(0)       // Score initial
                .strategieAbandon(null) // Pas de stratégie définie au départ
                .build();

        JoueurEntity savedJoueur = joueurRepository.save(joueurEntity);
        return mapToResponseDTO(savedJoueur);
    }

    public List<JoueurResponseDTO> getAllJoueurs() {
        return joueurRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public JoueurResponseDTO getJoueurById(Long id) {
        return joueurRepository.findById(id)
                .map(this::mapToResponseDTO)
                .orElseThrow(() -> new RuntimeException("Joueur non trouvé avec l'id : " + id));
    }

    public void deleteJoueur(Long id) {
        joueurRepository.deleteById(id);
    }

    private JoueurResponseDTO mapToResponseDTO(JoueurEntity joueurEntity) {
        return JoueurResponseDTO.builder()
                .id(joueurEntity.getId())
                .nom(joueurEntity.getNom())
                .abandon(joueurEntity.isAbandon())
                .score(joueurEntity.getScore())
                .strategieAbandon(joueurEntity.getStrategieAbandon())
                .build();
    }

 */
}
