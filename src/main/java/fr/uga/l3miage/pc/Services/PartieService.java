package fr.uga.l3miage.pc.Services;

import fr.uga.l3miage.pc.Components.PartieComponent;
import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.Exceptions.Rest.BadRequestRestException;
import fr.uga.l3miage.pc.Exceptions.Rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.Exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.Exceptions.Technical.NotFoundPartieEntityException;
import fr.uga.l3miage.pc.Mappers.PartieMapper;
import fr.uga.l3miage.pc.Repositories.PartieRepository;
import fr.uga.l3miage.pc.Requests.PartieRequestDTO;
import fr.uga.l3miage.pc.Responses.PartieResponseDTO;
import fr.uga.l3miage.pc.Responses.ServeurResponseDTO;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class PartieService {

    private final PartieMapper partieMapper;
    private final PartieComponent partieComponent;
    private final PartieRepository partieRepository;
    private final JoueurService joueurService;
    private final ServeurService serveurService;


    public PartieResponseDTO getPartieById(Long id) {
        try {
            PartieEntity partie = partieComponent.getPartieById(id);
            return partieMapper.toResponse(partie);
        } catch (NotFoundPartieEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }

    public PartieResponseDTO updatePartieStatus(Long id, String nouveauStatus) {
        try {
            PartieEntity partie = partieComponent.updateStatus(id, nouveauStatus);
            return partieMapper.toResponse(partie);
        } catch (NotFoundPartieEntityException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestRestException(e.getMessage());
        }
    }


    public PartieResponseDTO demarrerPartie(PartieRequestDTO request) {
        try {
            // Réserver le serveur pour la partie
            ServeurEntity serveur = serveurService.reserverServeur(request.getServeurId());

            // Récupérer les joueurs par leurs IDs
            List<JoueurEntity> joueurs = joueurService.getJoueursByIds(request.getJoueursIds());

            if (joueurs.size() != 2) {
                throw new BadRequestRestException("Il faut exactement deux joueurs pour commencer une partie.");
            }

            // Créer la partie avec le serveur et les joueurs associés
            PartieEntity nouvellePartie = PartieEntity.builder()
                    .nom(request.getNom())
                    .serveur(serveur)
                    .joueurs(joueurs)
                    .status("en cours")
                    .build();

            partieRepository.save(nouvellePartie);

            return partieMapper.toResponse(nouvellePartie);
        } catch (NotFoundEntityRestException e) {
            throw new NotFoundEntityRestException(e.getMessage());
        }
    }
}