package fr.uga.l3miage.pc.prisonersdilemma.Services;

import fr.uga.l3miage.pc.components.ServeurComponent;
import fr.uga.l3miage.pc.entities.PartieEntity;
import fr.uga.l3miage.pc.entities.ServeurEntity;
import fr.uga.l3miage.pc.exceptions.rest.BadRequestRestException;
import fr.uga.l3miage.pc.exceptions.rest.NotFoundEntityRestException;
import fr.uga.l3miage.pc.exceptions.technical.BadRequestException;
import fr.uga.l3miage.pc.exceptions.technical.NotFoundServeurEntityException;
import fr.uga.l3miage.pc.mappers.ServeurMapper;
import fr.uga.l3miage.pc.repositories.ServeurRepository;
import fr.uga.l3miage.pc.requests.ServeurRequestDTO;
import fr.uga.l3miage.pc.responses.ServeurResponseDTO;
import fr.uga.l3miage.pc.services.ServeurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class ServeurServiceTest {

    @Mock
    private ServeurMapper serveurMapper;

    @Mock
    private ServeurComponent serveurComponent;

    @Mock
    private ServeurRepository serveurRepository;

    @InjectMocks
    private ServeurService serveurService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateServeurWithActivePartie() {
        // Given
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("disponible");

        when(serveurRepository.existsByPartieNotNull()).thenReturn(true);

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.createServeur(request));
        verify(serveurRepository, times(1)).existsByPartieNotNull();
    }

    @Test
    void testDeleteServeurWithActivePartie() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .partie(new PartieEntity())  // Partie non terminée
                .build();

        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.deleteServeur(serveurId));
        verify(serveurRepository, times(1)).findById(serveurId);
    }

    @Test
    void testUpdateServeurNotFound() {
        // Given
        Long serveurId = 1L;
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus("disponible");

        when(serveurRepository.findById(serveurId)).thenReturn(Optional.empty());

        // When / Then
        assertThrows(NotFoundEntityRestException.class, () -> serveurService.updateServeur(serveurId, request));
        verify(serveurRepository, times(1)).findById(serveurId);
    }
    @Test
    void testCreateServeurBadRequest() {
        // Given
        ServeurRequestDTO request = new ServeurRequestDTO();
        request.setStatus(""); // Statut vide, invalid

        // When / Then
        assertThrows(BadRequestRestException.class, () -> serveurService.createServeur(request));
    }

    @Test
    void testDeleteServeurSuccess() {
        // Given
        Long serveurId = 1L;
        ServeurEntity serveurEntity = ServeurEntity.builder()
                .id(serveurId)
                .status("disponible")
                .partie(null) // Pas de partie en cours
                .build();

        when(serveurRepository.findById(serveurId)).thenReturn(Optional.of(serveurEntity));

        // When
        serveurService.deleteServeur(serveurId);

        // Then
        verify(serveurRepository, times(1)).deleteById(serveurId);
    }


}