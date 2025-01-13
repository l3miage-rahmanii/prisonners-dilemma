package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.PartieComponent;
import fr.uga.l3miage.pc.entities.JoueurEntity;
import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.mappers.PartieMapper;
import fr.uga.l3miage.pc.repositories.PartieRepository;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.JoueurService;
import fr.uga.l3miage.pc.services.PartieService;
import fr.uga.l3miage.pc.services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

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
    void testRejoindrePartieSuccess() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        PartieEntity partieEntity = new PartieEntity();
        partieEntity.setStatus("en_attente");

        JoueurEntity joueur = new JoueurEntity();
        joueur.setId(joueurId);

        PartieResponseDTO partieResponseDTO = PartieResponseDTO.builder()
                .id(partieId)
                .status("en_attente")
                .build();

        when(partieRepository.findById(partieId)).thenReturn(Optional.of(partieEntity));
        when(joueurService.getJoueurEntityById(joueurId)).thenReturn(joueur);
        when(partieMapper.toResponse(any())).thenReturn(partieResponseDTO);

        // When
        PartieResponseDTO result = partieService.rejoindrePartie(partieId, joueurId);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("en_attente");
        verify(partieRepository, times(1)).save(partieEntity);
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
        PartieResponseDTO result = partieService.abandonnerPartie(partieId, joueurId, strategie);

        // Then
        assertThat(result).isNotNull();
        verify(partieRepository, times(1)).save(partieEntity);
    }

}