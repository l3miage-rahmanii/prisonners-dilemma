package fr.uga.l3miage.pc.Components;

import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundJoueurEntityException;
import fr.uga.l3miage.pc.Repositories.JoueurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
public class JoueurComponent {

    private final JoueurRepository joueurRepository;

    public JoueurEntity getJoueurById(Long id) throws NotFoundJoueurEntityException {
        return joueurRepository.findById(id).orElseThrow(() -> new NotFoundJoueurEntityException("Joueur introuvable"));
    }

    public JoueurEntity updateScore(Long id, int nouveauScore) throws NotFoundJoueurEntityException, BadRequestException {
        JoueurEntity joueur = joueurRepository.findById(id).orElseThrow(() -> new NotFoundJoueurEntityException("Joueur introuvable"));
        if (nouveauScore < 0) {
            throw new BadRequestException("Le score doit être un entier positif");
        }
        joueur.setScore(nouveauScore);
        return joueurRepository.save(joueur);
    }

    public void deleteJoueur(Long id) throws NotFoundJoueurEntityException {
        JoueurEntity joueur = joueurRepository.findById(id).orElseThrow(() -> new NotFoundJoueurEntityException("Joueur introuvable"));
        joueurRepository.delete(joueur);
    }
}