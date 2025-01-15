package fr.uga.l3miage.pc.prisonersdilemma.Services;
/*
import fr.uga.l3miage.pc.components.PartieComponent;
import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.mappers.PartieMapper;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class PartieServiceTest {

    private PartieEntity partieEntity;
    private JoueurEntity joueur1;
    private JoueurEntity joueur2;
    private PartieResponseDTO partieResponseDTO;

    @Mock
    private PartieMapper partieMapper;

    PartieEntity partie;

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

        joueur1 = JoueurEntity.builder()
                .id(1L)
                .nom("Joueur 1")
                .build();

        joueur2 = JoueurEntity.builder()
                .id(2L)
                .nom("Joueur 2")
                .build();

        partieEntity = PartieEntity.builder()
                .id(1L)
                .status("en_attente")
                .joueurs(new ArrayList<>())
                .build();

        partieResponseDTO = new PartieResponseDTO(); // Initialize with appropriate fields
    }

    @Test
    void testJouerCoupSuccess() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "c";
        PartieEntity partieEntity = new PartieEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);

        partieEntity.setJoueurs(Arrays.asList(joueur1, joueur2));
        partieEntity.setCoupsJoueur1(new ArrayList<>());
        partieEntity.setCoupsJoueur2(new ArrayList<>());

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(partieId)
                .build();

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));
        when(partieMapper.toResponse(any())).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.jouerCoup(partieId, joueurId, coup);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(partieId);
        verify(partieRepository, times(1)).save(partieEntity);
    }

    @Test
    void testJouerCoupPartieNotFound() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "c";
        when(partieRepository.findById(partieId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> partieService.jouerCoup(partieId, joueurId, coup));
    }

    @Test
    void testJouerCoupInvalidCoup() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "x";
        PartieEntity partieEntity = new PartieEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);

        partieEntity.setJoueurs(Arrays.asList(joueur1, joueur2));

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> partieService.jouerCoup(partieId, joueurId, coup));
    }

    @Test
    void testCreerPartieSuccess() {
        // Given
        PartieRequestDTO partieRequestDTO = new PartieRequestDTO();
        partieRequestDTO.setNom("Nouvelle Partie");
        partieRequestDTO.setNbTours(5);

        PartieEntity partieEntity = new PartieEntity();
        partieEntity.setNom("Nouvelle Partie");
        partieEntity.setNbTours(5);

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(1L)
                .nom("Nouvelle Partie")
                .build();

        when(partieRepository.save(any())).thenReturn(partieEntity);
        when(partieMapper.toResponse(any())).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.creerPartie(partieRequestDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Nouvelle Partie");
        verify(partieRepository, times(1)).save(any());
    }

    @Test
    void testChangerStrategieSuccess() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        StrategieEnum strategie = StrategieEnum.DONNANT_DONNANT;

        PartieEntity partieEntity = new PartieEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(joueurId);
        partieEntity.setJoueurs(Arrays.asList(joueur1));

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(partieId)
                .build();

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));
        when(partieMapper.toResponse(any())).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.changerStrategie(partieId, joueurId, strategie);

        // Then
        assertThat(result).isNotNull();
        verify(partieRepository, times(1)).save(partieEntity);
    }

    @Test
    void rejoindrePartieSuccessFirstPlayer() {
        // Arrange
        Long partieId = 1L;
        Long joueurId = 1L;

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));
        when(joueurService.getJoueurEntityById(joueurId)).thenReturn(joueur1);
        when(partieRepository.save(any(PartieEntity.class))).thenReturn(partieEntity);
        when(partieMapper.toResponse(any(PartieEntity.class))).thenReturn(partieResponseDTO);

        // Act
        PartieResponseDTO result = partieService.rejoindrePartie(partieId, joueurId);

        // Assert
        assertNotNull(result);
        verify(partieRepository).findById(partieId);
        verify(joueurService).getJoueurEntityById(joueurId);
        verify(partieRepository).save(argThat(partie ->
                partie.getJoueurs().size() == 1 &&
                        partie.getJoueurs().contains(joueur1) &&
                        "en_attente".equals(partie.getStatus())
        ));
    }

    @Test
    void rejoindrePartieSuccessSecondPlayer() {
        // Arrange
        Long partieId = 1L;
        Long joueurId = 2L;
        partieEntity.getJoueurs().add(joueur1);

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));
        when(joueurService.getJoueurEntityById(joueurId)).thenReturn(joueur2);
        when(partieRepository.save(any(PartieEntity.class))).thenReturn(partieEntity);
        when(partieMapper.toResponse(any(PartieEntity.class))).thenReturn(partieResponseDTO);

        // Act
        PartieResponseDTO result = partieService.rejoindrePartie(partieId, joueurId);

        // Assert
        assertNotNull(result);
        verify(partieRepository).save(argThat(partie ->
                partie.getJoueurs().size() == 2 &&
                        partie.getJoueurs().contains(joueur1) &&
                        partie.getJoueurs().contains(joueur2) &&
                        "en_cours".equals(partie.getStatus())
        ));
    }

    @Test
    void testRejoindrePartiePartieComplete() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        PartieEntity partieEntity = new PartieEntity();
        partieEntity.setStatus("en_attente");
        partieEntity.setJoueurs(Arrays.asList(new JoueurEntity(), new JoueurEntity()));

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> partieService.rejoindrePartie(partieId, joueurId));
    }
    @Test
    void testAbandonnerPartieSuccess() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        StrategieEnum strategie = StrategieEnum.RANCUNIER;

        PartieEntity partieEntity = new PartieEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        partieEntity.setJoueurs(Arrays.asList(joueur1));

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(partieId)
                .build();

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));
        when(partieMapper.toResponse(any())).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.changerStrategie(partieId, joueurId, strategie);

        // Then
        assertThat(result).isNotNull();
        verify(partieRepository, times(1)).save(partieEntity);
    }


    @Test
    void testRejoindrePartiePasDePlace() {
        // Given
        Long partieId = 1L;
        Long joueurId = 3L;
        partieEntity.setJoueurs(Arrays.asList(new JoueurEntity(), new JoueurEntity())); // La partie est pleine

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> partieService.rejoindrePartie(partieId, joueurId));
    }


    @Test
    void testChangerStrategiePartieInexistante() {
        // Given
        Long partieId = 999L;
        Long joueurId = 1L;
        StrategieEnum strategie = StrategieEnum.DONNANT_DONNANT;

        when(partieRepository.findById(partieId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> partieService.changerStrategie(partieId, joueurId, strategie));
    }

    @Test
    void testJouerCoupJoueurNonParticipant() {
        // Given
        Long partieId = 1L;
        Long joueurId = 999L; // Joueur non participant
        String coup = "c";
        PartieEntity partieEntity = new PartieEntity();
        JoueurEntity joueur1 = new JoueurEntity();
        joueur1.setId(1L);
        JoueurEntity joueur2 = new JoueurEntity();
        joueur2.setId(2L);
        partieEntity.setJoueurs(Arrays.asList(joueur1, joueur2));

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> partieService.jouerCoup(partieId, joueurId, coup));
    }


    @Test
    void jouerCoup_whenJoueur1HasStrategy_shouldUseStrategyToPlay() {
        // Given
        Long partieId = 1L;
        Long joueurId = 10L;
        String coup = "c";  // This will be ignored as strategy is used

        JoueurEntity joueur1 = JoueurEntity.builder().id(10L).build();
        JoueurEntity joueur2 = JoueurEntity.builder().id(20L).build();

        PartieEntity partie = PartieEntity.builder()
                .id(partieId)
                .joueurs(List.of(joueur1, joueur2))
                .strategieJoueur1(StrategieEnum.TOUJOURS_COOPERER)
                .coupsJoueur1(new ArrayList<>())
                .coupsJoueur2(new ArrayList<>())
                .build();

        PartieResponseDTO expectedResponse = new PartieResponseDTO();

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partie));
        when(partieRepository.save(any())).thenReturn(partie);
        when(partieMapper.toResponse(partie)).thenReturn(expectedResponse);

        // When
        PartieResponseDTO result = partieService.jouerCoup(partieId, joueurId, coup);

        // Then
        verify(partieRepository).findById(partieId);
        verify(partieRepository).save(partie);
        assertEquals("c", partie.getCoupsJoueur1().get(0)); // Strategy TOUJOURS_COOPERER should play "c"
        assertEquals(expectedResponse, result);
    }

    @Test
    void jouerCoup_whenInvalidCoup_shouldThrowBadRequestException() {
        // Given
        Long partieId = 1L;
        Long joueurId = 10L;
        String invalidCoup = "x"; // Invalid move - neither "c" nor "t"

        JoueurEntity joueur1 = JoueurEntity.builder().id(10L).build();
        JoueurEntity joueur2 = JoueurEntity.builder().id(20L).build();

        PartieEntity partie = PartieEntity.builder()
                .id(partieId)
                .joueurs(List.of(joueur1, joueur2))
                .coupsJoueur1(new ArrayList<>())
                .coupsJoueur2(new ArrayList<>())
                .build();

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partie));

        // When & Then
        assertThrows(
                BadRequestRestException.class,
                () -> partieService.jouerCoup(partieId, joueurId, invalidCoup)
        );
    }

    @Test
    void jouerCoup_whenJoueur2HasStrategy_shouldUseStrategyToPlay() {
        // Given
        Long partieId = 1L;
        Long joueurId = 20L;
        String coup = "c";  // This will be ignored as strategy is used

        JoueurEntity joueur1 = JoueurEntity.builder().id(10L).build();
        JoueurEntity joueur2 = JoueurEntity.builder().id(20L).build();

        PartieEntity partie = PartieEntity.builder()
                .id(partieId)
                .joueurs(List.of(joueur1, joueur2))
                .strategieJoueur2(StrategieEnum.TOUJOURS_TRAHIR)
                .coupsJoueur1(new ArrayList<>())
                .coupsJoueur2(new ArrayList<>())
                .build();

        PartieResponseDTO expectedResponse = new PartieResponseDTO();

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partie));
        when(partieRepository.save(any())).thenReturn(partie);
        when(partieMapper.toResponse(partie)).thenReturn(expectedResponse);

        // When
        PartieResponseDTO result = partieService.jouerCoup(partieId, joueurId, coup);

        // Then
        verify(partieRepository).findById(partieId);
        verify(partieRepository).save(partie);
        assertEquals("t", partie.getCoupsJoueur2().get(0)); // Strategy TOUJOURS_TRAHIR should play "t"
        assertEquals(expectedResponse, result);
    }



}

 */

import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.enums.CoupEnum;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.mappers.PartieMapper;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PartieServiceTest {

    private PartieService partieService;

    @Mock
    private PartieMapper partieMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        partieService = new PartieService(partieMapper);
        // Initialize the partie field manually since it's not injected
        partieService.partie = PartieEntity.builder()
                .idJoueur1(1)
                .idJoueur2(2)
                .status("en_cours")
                .nbTours(5)
                .coupsJoueur1(new ArrayList<>())
                .coupsJoueur2(new ArrayList<>())
                .build();
    }

    @Test
    void jouerCoup_ShouldThrowException_WhenNotPlayerTurn() {
        // Arrange
        int joueurId = 1; // Joueur 2
        CoupEnum coup = CoupEnum.COOPERER;
    }
}