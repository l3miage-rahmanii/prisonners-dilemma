    package fr.uga.l3miage.pc.services;

    import fr.uga.l3miage.pc.components.PartieComponent;
    import fr.uga.l3miage.pc.entities.JoueurEntity;
    import fr.uga.l3miage.pc.entities.PartieEntity;
    import fr.uga.l3miage.pc.enums.StrategieEnum;
    import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
    import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
    import fr.uga.l3miage.pc.mappers.PartieMapper;
    import fr.uga.l3miage.pc.repositories.PartieRepository;
    import fr.uga.l3miage.pc.requests.PartieRequestDTO;
    import fr.uga.l3miage.pc.responses.PartieResponseDTO;
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
        private String partieNonTrouve = "Partie non trouvée";
        private String enCours = "en_cours";

        public PartieResponseDTO jouerCoup(Long partieId, Long joueurId, String coup) {
            PartieEntity partie = partieRepository.findById(partieId)
                    .orElseThrow(() -> new NotFoundEntityRestException(partieNonTrouve));


            boolean estJoueur1 = partie.getJoueurs().get(0).getId().equals(joueurId);

            // Si le joueur a une stratégie automatique, utiliser celle-ci
            if ((estJoueur1 && partie.getStrategieJoueur1() != null) ||
                    (!estJoueur1 && partie.getStrategieJoueur2() != null)) {
                coup = determinerCoupStrategie(partie, estJoueur1);
            }
            // Vérifier que le joueur fait partie de la partie
            if (!partie.getJoueurs().stream().anyMatch(j -> j.getId().equals(joueurId))) {
                throw new BadRequestRestException("Ce joueur ne fait pas partie de cette partie");
            }

            // Vérifier que le coup est valide
            if (!coup.equals("c") && !coup.equals("t")) {
                throw new BadRequestRestException("Coup invalide. Utilisez 'c' pour coopérer ou 't' pour trahir");
            }

            // Enregistrer le coup
            if (estJoueur1) {
                partie.getCoupsJoueur1().add(coup);
            } else {
                partie.getCoupsJoueur2().add(coup);
            }

            // Si les deux joueurs ont joué, calculer les scores
            if (partie.getCoupsJoueur1().size() == partie.getCoupsJoueur2().size()) {
                calculerScores(partie);
            }

            // Sauvegarder et retourner la partie mise à jour
            return partieMapper.toResponse(partieRepository.save(partie));
        }

        public PartieResponseDTO creerPartie(PartieRequestDTO partieRequestDTO) {
            PartieEntity partie = PartieEntity.builder()
                    .nom(partieRequestDTO.getNom())
                    .status("en_attente")  // en_attente -> en_cours -> terminée
                    .nbTours(partieRequestDTO.getNbTours()) // Ajouter ce champ dans PartieEntity
                    .build();
            return partieMapper.toResponse(partieRepository.save(partie));
        }

        public PartieResponseDTO changerStrategie(Long partieId, Long joueurId, StrategieEnum strategie) {
            PartieEntity partie = partieRepository.findById(partieId)
                    .orElseThrow(() -> new NotFoundEntityRestException(partieNonTrouve));

            boolean estJoueur1 = partie.getJoueurs().get(0).getId().equals(joueurId);
            if (estJoueur1) {
                partie.setStrategieJoueur1(strategie);
            } else {
                partie.setStrategieJoueur2(strategie);
            }

            return partieMapper.toResponse(partieRepository.save(partie));
        }

        private String determinerCoupStrategie(PartieEntity partie, boolean estJoueur1) {
            StrategieEnum strategie = estJoueur1 ? partie.getStrategieJoueur1() : partie.getStrategieJoueur2();
            List<String> mesCoups = estJoueur1 ? partie.getCoupsJoueur1() : partie.getCoupsJoueur2();
            List<String> coupsAdversaire = estJoueur1 ? partie.getCoupsJoueur2() : partie.getCoupsJoueur1();

            switch (strategie) {
                case DONNANT_DONNANT:
                    if (coupsAdversaire.isEmpty()) return "c";
                    return coupsAdversaire.get(coupsAdversaire.size() - 1);

                case RANCUNIER:
                    if (coupsAdversaire.contains("t")) return "t";
                    return "c";

                case TOUJOURS_TRAHIR:
                    return "t";

                case TOUJOURS_COOPERER:
                    return "c";



                default:
                    return "c";
            }
        }

        public PartieResponseDTO rejoindrePartie(Long partieId, Long joueurId) {
            PartieEntity partie = partieRepository.findById(partieId)
                    .orElseThrow(() -> new NotFoundEntityRestException(partieNonTrouve));

            JoueurEntity joueur = joueurService.getJoueurEntityById(joueurId);

            if (partie.getJoueurs().size() >= 2) {
                throw new BadRequestRestException("La partie est déjà complète");
            }

            if (partie.getJoueurs().stream().anyMatch(j -> j.getId().equals(joueurId))) {
                throw new BadRequestRestException("Le joueur est déjà dans la partie");
            }

            partie.getJoueurs().add(joueur); // Ajouter directement l'entité

            if (partie.getJoueurs().size() == 2) {
                partie.setStatus(enCours);
            }

            return partieMapper.toResponse(partieRepository.save(partie));
        }


        private void calculerScores(PartieEntity partie) {
            int dernierIndex = partie.getCoupsJoueur1().size() - 1;
            String coupJ1 = partie.getCoupsJoueur1().get(dernierIndex);
            String coupJ2 = partie.getCoupsJoueur2().get(dernierIndex);

            // Dilemme du prisonnier classique
            if (coupJ1.equals("c") && coupJ2.equals("c")) {
                // Coopération mutuelle
                partie.setScoreJoueur1(partie.getScoreJoueur1() + 3);
                partie.setScoreJoueur2(partie.getScoreJoueur2() + 3);
            } else if (coupJ1.equals("t") && coupJ2.equals("t")) {
                // Trahison mutuelle
                partie.setScoreJoueur1(partie.getScoreJoueur1() + 1);
                partie.setScoreJoueur2(partie.getScoreJoueur2() + 1);
            } else if (coupJ1.equals("t") && coupJ2.equals("c")) {
                // J1 trahit, J2 coopère
                partie.setScoreJoueur1(partie.getScoreJoueur1() + 5);
                partie.setScoreJoueur2(partie.getScoreJoueur2() + 0);
            } else {
                // J1 coopère, J2 trahit
                partie.setScoreJoueur1(partie.getScoreJoueur1() + 0);
                partie.setScoreJoueur2(partie.getScoreJoueur2() + 5);
            }

            // Vérifier si la partie est terminée (par exemple, après 10 tours)
            if (partie.getCoupsJoueur1().size() >= 10) {
                partie.setStatus("terminée");

                // Mettre à jour les scores des joueurs
                JoueurEntity joueur1 = partie.getJoueurs().get(0);
                JoueurEntity joueur2 = partie.getJoueurs().get(1);

                joueur1.setScore(partie.getScoreJoueur1());
                joueur2.setScore(partie.getScoreJoueur2());
            }
        }
    }