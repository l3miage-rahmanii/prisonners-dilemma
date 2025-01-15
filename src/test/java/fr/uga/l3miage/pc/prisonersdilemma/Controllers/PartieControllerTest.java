package fr.uga.l3miage.pc.prisonersdilemma.Controllers;

import fr.uga.l3miage.pc.controllers.PartieController;
import fr.uga.l3miage.pc.enums.StrategieEnum;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.requests.PartieRequestDTO;
import fr.uga.l3miage.pc.responses.PartieResponseDTO;
import fr.uga.l3miage.pc.services.PartieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PartieControllerTest {
/*
    @Mock
    private PartieService partieService;

    @InjectMocks
    private PartieController partieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testJouerCoupCooperer() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "cooperer";

        PartieResponseDTO mockResponse = createMockPartieResponse(true, "c");
        when(partieService.jouerCoup(partieId, joueurId, "c")).thenReturn(mockResponse);

        // When
        ResponseEntity<String> response = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Coup joue"));
        assertTrue(response.getBody().contains("Score: 10"));
        verify(partieService).jouerCoup(partieId, joueurId, "c");
    }

    @Test
    void testJouerCoupTrahir() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "trahir";

        PartieResponseDTO mockResponse = createMockPartieResponse(true, "t");
        when(partieService.jouerCoup(partieId, joueurId, "t")).thenReturn(mockResponse);

        // When
        ResponseEntity<String> response = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Coup joue"));
        verify(partieService).jouerCoup(partieId, joueurId, "t");
    }


    @Test
    void testJouerCoupInvalide() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "invalid";

        // When
        ResponseEntity<String> response = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Coup invalide", response.getBody());
    }

    @Test
    void testJouerCoupPartieTerminee() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "cooperer";

        PartieResponseDTO mockResponse = PartieResponseDTO.builder()
                .partieTerminee(true)
                .messageResultat("Partie terminée ! Joueur 1 gagne")
                .coupsJoueur1(Arrays.asList("c"))
                .scoreJoueur1(10)
                .build();

        when(partieService.jouerCoup(partieId, joueurId, "c")).thenReturn(mockResponse);

        // When
        ResponseEntity<String> response = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Partie terminée ! Joueur 1 gagne"));
    }

    @Test
    void testJouerCoupServiceException() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "cooperer";

        when(partieService.jouerCoup(partieId, joueurId, "c"))
                .thenThrow(new RuntimeException("Erreur service"));

        // When
        ResponseEntity<String> response = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erreur service", response.getBody());
    }

    // Méthode utilitaire pour créer un mock de PartieResponseDTO
    private PartieResponseDTO createMockPartieResponse(boolean isJoueur1, String coup) {
        return PartieResponseDTO.builder()
                .partieTerminee(false)
                .tourActuel(5)
                .coupsJoueur1(isJoueur1 ? Arrays.asList(coup) : new ArrayList<>())
                .coupsJoueur2(!isJoueur1 ? Arrays.asList(coup) : new ArrayList<>())
                .scoreJoueur1(10)
                .scoreJoueur2(8)
                .build();
    }

    @Test
    void testCreerPartie() {
        // Given
        PartieRequestDTO partieRequestDTO = new PartieRequestDTO();
        partieRequestDTO.setNom("Partie test");
        partieRequestDTO.setNbTours(10);

        PartieResponseDTO response = new PartieResponseDTO();
        response.setId(1L);
        response.setNom("Partie test");
        response.setStatus("en_attente");

        when(partieService.creerPartie(partieRequestDTO)).thenReturn(response);

        // When
        ResponseEntity<PartieResponseDTO> result = partieController.creerPartie(partieRequestDTO);

        // Then
        PartieResponseDTO actualResponse = result.getBody(); // Récupérer le body
        assertNotNull(actualResponse);
        assertEquals("Partie test", actualResponse.getNom());
        assertEquals("en_attente", actualResponse.getStatus());
        verify(partieService, times(1)).creerPartie(partieRequestDTO);
    }


    @Test
    void testChangerStrategie() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        StrategieEnum strategie = StrategieEnum.DONNANT_DONNANT;

        PartieResponseDTO response = new PartieResponseDTO();
        response.setId(partieId);
        response.setStatus("En cours");

        when(partieService.changerStrategie(partieId, joueurId, strategie)).thenReturn(response);

        // When
        ResponseEntity<PartieResponseDTO> result = partieController.changerStrategie(partieId, joueurId, strategie);

        // Then
        PartieResponseDTO actualResponse = result.getBody();
        assertNotNull(actualResponse);
        assertEquals(partieId, actualResponse.getId());
        assertEquals("En cours", actualResponse.getStatus());
        verify(partieService, times(1)).changerStrategie(partieId, joueurId, strategie);
    }


    @Test
    void testRejoindrePartie() {
        // Given
        Long partieId = 1L;
        Long joueurId = 2L;

        PartieResponseDTO response = new PartieResponseDTO();
        response.setId(partieId);
        response.setStatus("en_cours");

        when(partieService.rejoindrePartie(partieId, joueurId)).thenReturn(response);

        // When
        ResponseEntity<PartieResponseDTO> result = partieController.rejoindrePartie(partieId, joueurId);

        // Then
        PartieResponseDTO actualResponse = result.getBody();
        assertNotNull(actualResponse);
        assertEquals(partieId, actualResponse.getId());
        assertEquals("en_cours", actualResponse.getStatus());
        verify(partieService, times(1)).rejoindrePartie(partieId, joueurId);
    }


    @Test
    void testRejoindrePartie_DejaDansPartie() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;  // Joueur déjà dans la partie
        when(partieService.rejoindrePartie(partieId, joueurId)).thenThrow(new BadRequestRestException("Le joueur est dejà dans la partie"));

        // When & Then
        BadRequestRestException thrown = assertThrows(BadRequestRestException.class, () -> {
            partieController.rejoindrePartie(partieId, joueurId);
        });
        assertEquals("Le joueur est dejà dans la partie", thrown.getMessage());
    }



    @Test
    void testJouerCoupAbandonner() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "abandonner";

        // When
        ResponseEntity<String> result = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Partie abandonnee", result.getBody());

        verify(partieService, times(1)).changerStrategie(partieId, joueurId, StrategieEnum.TOUJOURS_TRAHIR);
    }

    @Test
    void testJouerCoupReinitialiser() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "reinitialiser";

        PartieRequestDTO newGame = PartieRequestDTO.builder()
                .nom("Nouvelle Partie")
                .nbTours(10)
                .build();

        PartieResponseDTO newPartie = new PartieResponseDTO();
        newPartie.setId(2L);
        newPartie.setNom("Nouvelle Partie");

        when(partieService.creerPartie(any(PartieRequestDTO.class))).thenReturn(newPartie);

        // When
        ResponseEntity<String> result = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(200, result.getStatusCodeValue());
        assertEquals("Nouvelle partie creee: 2", result.getBody());

        verify(partieService, times(1)).creerPartie(any(PartieRequestDTO.class));
    }

@Test
void testCreerPartieInvalide() {
    // Given
    PartieRequestDTO partieRequestDTO = new PartieRequestDTO();
    partieRequestDTO.setNom("");  // Nom vide, invalide
    partieRequestDTO.setNbTours(-1);  // Nombre de tours invalide

    when(partieService.creerPartie(partieRequestDTO)).thenThrow(new BadRequestRestException("Nom de la partie et nombre de tours sont obligatoires"));

    // When & Then
    BadRequestRestException thrown = assertThrows(BadRequestRestException.class, () -> {
        partieController.creerPartie(partieRequestDTO);
    });

    assertEquals("Nom de la partie et nombre de tours sont obligatoires", thrown.getMessage());
    verify(partieService, times(1)).creerPartie(partieRequestDTO);
}

 */

}
