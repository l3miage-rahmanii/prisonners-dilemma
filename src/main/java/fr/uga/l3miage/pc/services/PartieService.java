package fr.uga.l3miage.pc.services;

import fr.uga.l3miage.pc.components.PartieComponent;
import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.Rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.Rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.Technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.Technical.NotFoundPartieEntityException;
import fr.uga.l3miage.pc.Mappers.PartieMapper;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.Responses.PartieResponseDTO;
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
            // R�server le serveur pour la partie
            ServeurEntity serveur = serveurService.reserverServeur(request.getServeurId());

            // R�cup�rer les joueurs par leurs IDs
            List<JoueurEntity> joueurs = joueurService.getJoueursByIds(request.getJoueursIds());

            if (joueurs.size() != 2) {
                throw new BadRequestRestException("Il faut exactement deux joueurs pour commencer une partie.");
            }

            // Cr�er la partie avec le serveur et les joueurs associ�s
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