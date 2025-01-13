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
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class PartieControllerTest {

    @Mock
    private PartieService partieService;

    @InjectMocks
    private PartieController partieController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testJouerCoup() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "c"; // Coopérer
        PartieResponseDTO response = new PartieResponseDTO();
        response.setId(partieId);
        response.setStatus("En cours");

        // Simule la réponse du service
        when(partieService.jouerCoup(partieId, joueurId, coup)).thenReturn(response);

        // When
        ResponseEntity<PartieResponseDTO> result = partieController.jouerCoup(partieId, joueurId, coup);

        // Then
        assertEquals(partieId, result.getBody().getId());
        assertEquals("En cours", result.getBody().getStatus());
        verify(partieService, times(1)).jouerCoup(partieId, joueurId, coup);
    }

    @Test
    void testJouerCoup_Invalide() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;
        String coup = "invalid"; // Coup invalide
        when(partieService.jouerCoup(partieId, joueurId, coup)).thenThrow(new BadRequestRestException("Coup invalide. Utilisez 'c' pour coopérer ou 't' pour trahir"));

        // When & Then
        BadRequestRestException thrown = assertThrows(BadRequestRestException.class, () -> {
            partieController.jouerCoup(partieId, joueurId, coup);
        });
        assertEquals("Coup invalide. Utilisez 'c' pour coopérer ou 't' pour trahir", thrown.getMessage());
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
        PartieResponseDTO result = partieController.creerPartie(partieRequestDTO);

        // Then
        assertEquals("Partie test", result.getNom());
        assertEquals("en_attente", result.getStatus());
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
        PartieResponseDTO result = partieController.changerStrategie(partieId, joueurId, strategie);

        // Then
        assertEquals(partieId, result.getId());
        assertEquals("En cours", result.getStatus());
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
        PartieResponseDTO result = partieController.rejoindrePartie(partieId, joueurId);

        // Then
        assertEquals(partieId, result.getId());
        assertEquals("en_cours", result.getStatus());
        verify(partieService, times(1)).rejoindrePartie(partieId, joueurId);
    }

    @Test
    void testRejoindrePartie_DejaDansPartie() {
        // Given
        Long partieId = 1L;
        Long joueurId = 1L;  // Joueur déjà dans la partie
        when(partieService.rejoindrePartie(partieId, joueurId)).thenThrow(new BadRequestRestException("Le joueur est déjà dans la partie"));

        // When & Then
        BadRequestRestException thrown = assertThrows(BadRequestRestException.class, () -> {
            partieController.rejoindrePartie(partieId, joueurId);
        });
        assertEquals("Le joueur est déjà dans la partie", thrown.getMessage());
    }
}
