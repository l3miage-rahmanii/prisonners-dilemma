package fr.uga.l3miage.pc.components;

import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundJoueurEntityException;
import fr.uga.l3miage.pc.repositories.JoueurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class JoueurComponent {

    private final JoueurRepository joueurRepository;
    private String joueurNonTrouve = "Joueur introuvable";

    public JoueurEntity getJoueurById(Long id) throws NotFoundJoueurEntityException {
        return joueurRepository.findById(id).orElseThrow(() -> new NotFoundJoueurEntityException(joueurNonTrouve));
    }

    public JoueurEntity updateScore(Long id, int nouveauScore) throws NotFoundJoueurEntityException, BadRequestException {
        JoueurEntity joueur = joueurRepository.findById(id).orElseThrow(() -> new NotFoundJoueurEntityException(joueurNonTrouve));
        if (nouveauScore < 0) {
            throw new BadRequestException("Le score doit être un entier positif");
        }
        joueur.setScore(nouveauScore);
        return joueurRepository.save(joueur);
    }

    public void deleteJoueur(Long id) throws NotFoundJoueurEntityException {
        JoueurEntity joueur = joueurRepository.findById(id).orElseThrow(() -> new NotFoundJoueurEntityException(joueurNonTrouve));
        joueurRepository.delete(joueur);
    }

}