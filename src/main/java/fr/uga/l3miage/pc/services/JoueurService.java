package fr.uga.l3miage.pc.services;

import fr.uga.l3miage.pc.components.JoueurComponent;
import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundJoueurEntityException;
import fr.uga.l3miage.pc.mappers.JoueurMapper;
import fr.uga.l3miage.pc.repositories.JoueurRepository;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JoueurService {

    private final JoueurMapper joueurMapper;
    private final JoueurComponent joueurComponent;
    private final JoueurRepository joueurRepository;

    public JoueurResponseDTO getJoueurById(Long id) {
        try {
            JoueurEntity joueur = joueurComponent.getJoueurById(id);
            return joueurMapper.toResponse(joueur);
        } catch (NotFoundJoueurEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

    public JoueurResponseDTO updateScore(Long id, int nouveauScore) {
        try {
            JoueurEntity joueur = joueurComponent.updateScore(id, nouveauScore);
            return joueurMapper.toResponse(joueur);
        } catch (NotFoundJoueurEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }

    public List<JoueurEntity> getJoueursByIds(List<Long> ids) {
        List<JoueurEntity> joueurs = joueurRepository.findAllById(ids);
        if (joueurs.size() != ids.size()) {
            throw new NotFoundEntityRestException("Certains joueurs n'ont pas été trouvés");
        }
        return joueurs;
    }
}