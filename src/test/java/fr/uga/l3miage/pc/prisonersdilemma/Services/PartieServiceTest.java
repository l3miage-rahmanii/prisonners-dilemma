package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.PartieComponent;
import fr.uga.l3miage.pc.Entities.JoueurEntity;
import fr.uga.l3miage.pc.Entities.PartieEntity;
import fr.uga.l3miage.pc.Entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.Mappers.PartieMapper;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.Services.JoueurService;
import fr.uga.l3miage.pc.Services.PartieService;
import fr.uga.l3miage.pc.Services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class PartieServiceTest {

    @Mock
    private PartieMapper partieMapper;

    @Mock
    private PartieComponent partieComponent;

    @Mock
    private PartieRepository partieRepository;

    @Mock
    private JoueurService joueurService;

    @Mock
    private ServeurService serveurService;

    @InjectMocks
    private PartieService partieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPartieByIdSuccess() throws Exception {
        // Given
        Long partieId = 1L;
        PartieEntity partieEntity = new PartieEntity();
        partieEntity.setId(partieId);
        partieEntity.setNom("Partie Test");

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(partieId)
                .nom("Partie Test")
                .build();

        when(partieComponent.getPartieById(partieId)).thenReturn(partieEntity);
        when(partieMapper.toResponse(partieEntity)).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.getPartieById(partieId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Partie Test");
        verify(partieComponent, times(1)).getPartieById(partieId);
        verify(partieMapper, times(1)).toResponse(partieEntity);
    }

    @Test
    void testGetPartieByIdNotFound() throws Exception {
        // Given
        Long partieId = 1L;
        when(partieComponent.getPartieById(partieId)).thenThrow(new NotFoundEntityRestException("Partie introuvable"));

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> partieService.getPartieById(partieId));
        verify(partieComponent, times(1)).getPartieById(partieId);
    }

    @Test
    void testUpdatePartieStatusSuccess() throws Exception {
        // Given
        Long partieId = 1L;
        String nouveauStatus = "terminée";

        PartieEntity partieEntity = new PartieEntity();
        partieEntity.setId(partieId);
        partieEntity.setStatus(nouveauStatus);

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(partieId)
                .status(nouveauStatus)
                .build();

        when(partieComponent.updateStatus(partieId, nouveauStatus)).thenReturn(partieEntity);
        when(partieMapper.toResponse(partieEntity)).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.updatePartieStatus(partieId, nouveauStatus);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo(nouveauStatus);
        verify(partieComponent, times(1)).updateStatus(partieId, nouveauStatus);
        verify(partieMapper, times(1)).toResponse(partieEntity);
    }

    @Test
    void testUpdatePartieStatusBadRequest() throws Exception {
        // Given
        Long partieId = 1L;
        String nouveauStatus = "";

        when(partieComponent.updateStatus(partieId, nouveauStatus)).thenThrow(new BadRequestRestException("Statut invalide"));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> partieService.updatePartieStatus(partieId, nouveauStatus));
        verify(partieComponent, times(1)).updateStatus(partieId, nouveauStatus);
    }

    @Test
    void testDemarrerPartieSuccess() {
        // Given
        PartieRequestDTO request = new PartieRequestDTO();
        request.setNom("Nouvelle Partie");
        request.setServeurId(1L);
        request.setJoueursIds(Arrays.asList(1L, 2L));

        ServeurEntity serveur = new ServeurEntity();
        serveur.setId(1L);

        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);

        PartieEntity partieEntity = new PartieEntity();
        partieEntity.setId(1L);
        partieEntity.setNom("Nouvelle Partie");
        partieEntity.setServeur(serveur);
        partieEntity.setJoueurs(Arrays.asList(joueur1, joueur2));
        partieEntity.setStatus("en cours");

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(1L)
                .nom("Nouvelle Partie")
                .status("en cours")
                .build();

        when(serveurService.reserverServeur(request.getServeurId())).thenReturn(serveur);
        when(joueurService.getJoueursByIds(request.getJoueursIds())).thenReturn(Arrays.asList(joueur1, joueur2));
        when(partieMapper.toResponse(any(PartieEntity.class))).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.demarrerPartie(request);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Nouvelle Partie");
        assertThat(result.getStatus()).isEqualTo("en cours");
        verify(partieRepository, times(1)).save(any(PartieEntity.class));
        verify(partieMapper, times(1)).toResponse(any(PartieEntity.class));
    }

    @Test
    void testDemarrerPartieBadRequest() {
        // Given
        PartieRequestDTO request = new PartieRequestDTO();
        request.setNom("Nouvelle Partie");
        request.setServeurId(1L);
        request.setJoueursIds(Arrays.asList(1L)); // Only one player

        when(joueurService.getJoueursByIds(request.getJoueursIds()))
                .thenThrow(new BadRequestRestException("Il faut exactement deux joueurs pour commencer une partie."));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> partieService.demarrerPartie(request));
        verify(joueurService, times(1)).getJoueursByIds(request.getJoueursIds());
    }
}