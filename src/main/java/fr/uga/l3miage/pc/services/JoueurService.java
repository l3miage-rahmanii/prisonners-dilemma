package fr.uga.l3miage.pc.services;

import fr.uga.l3miage.pc.components.JoueurComponent;
import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundJoueurEntityException;
import fr.uga.l3miage.pc.mappers.JoueurMapper;
import fr.uga.l3miage.pc.repositories.JoueurRepository;
import fr.uga.l3miage.pc.requests.JoueurRequestDTO;
import fr.uga.l3miage.pc.responses.JoueurResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public JoueurEntity getJoueurEntityById(Long id) {
        return joueurRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityRestException("Joueur non trouv�"));
    }

    public JoueurResponseDTO creerJoueur(JoueurRequestDTO joueurRequestDTO) {
        JoueurEntity joueur = JoueurEntity.builder()
                .nom(joueurRequestDTO.getNom())
                .score(0)
                .nbVictoires(0)
                .nbParties(0)
                .scoreMoyen(0.0)
                .tauxCooperation(0.0)
                .build();

        return joueurMapper.toResponse(joueurRepository.save(joueur));
    }


    public JoueurResponseDTO getStatistiques(Long joueurId) {
        JoueurEntity joueur = getJoueurEntityById(joueurId);

        double tauxVictoire = joueur.getNbParties() > 0 ?
                (double) joueur.getNbVictoires() / joueur.getNbParties() * 100 : 0;

        joueur.setTauxVictoire(tauxVictoire);

        return joueurMapper.toResponse(joueurRepository.save(joueur));
    }

    private void mettreAJourStrategiesPreferees(JoueurEntity joueur, PartieEntity partie, boolean estJoueur1) {
        StrategieEnum strategie = estJoueur1 ? partie.getStrategieJoueur1() : partie.getStrategieJoueur2();
        if (strategie != null) {
            Map<StrategieEnum, Integer> strategiesUtilisees = joueur.getStrategiesUtilisees();
            if (strategiesUtilisees == null) {
                strategiesUtilisees = new HashMap<>();
            }
            strategiesUtilisees.merge(strategie, 1, Integer::sum);
            joueur.setStrategiesUtilisees(strategiesUtilisees);
        }
    }


    public JoueurEntity updateDernierCoup(Long id, String dernierCoup) {
        JoueurEntity joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityRestException("Joueur non trouv�"));
        joueur.setDernierCoup(dernierCoup);
        return joueurRepository.save(joueur);
    }

    public JoueurEntity updateScore(Long id, int points) {
        JoueurEntity joueur = joueurRepository.findById(id)
                .orElseThrow(() -> new NotFoundEntityRestException("Joueur non trouv�"));
        joueur.setScore(joueur.getScore() + points);
        return joueurRepository.save(joueur);
    }

    public List<JoueurEntity> getJoueursByIds(List<Long> ids) {
        List<JoueurEntity> joueurs = joueurRepository.findAllById(ids);
        if (joueurs.size() != ids.size()) {
            throw new NotFoundEntityRestException("Certains joueurs n'ont pas �t� trouv�s");
        }
        return joueurs;
    }
}